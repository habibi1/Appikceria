package com.bkipmlampung.appikceria;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bkipmlampung.appikceria.model.RegisterResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.DeviceIDUtil;
import com.bkipmlampung.appikceria.utils.RealPathUtil;
import com.bkipmlampung.appikceria.utils.SharedPreference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.bkipmlampung.appikceria.utils.ControlUtil.elapseClick;

public class RegistrasiActivity extends AppCompatActivity implements View.OnClickListener {

    private int PHONE_STATE_CODE = 1;
    private int CAMERA_STATE_CODE = 3;

    MaterialButton buttonNext;

    //ProgressBar progressBar;

    ProgressDialog progressDialog;

    SharedPreference sharedPreference;

    TextView textViewNomorTelepon;
    ImageView imageViewProfil;
    TextInputEditText editTextNama, editTextPassword, editTextAlamat, editTextEmail, editTextLampiran;
    TextInputLayout layoutNama, layoutPassword, layoutAlamat, layoutEmail, layoutLampiran;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    CardView dialogCardViewCamera, dialogCardViewGalery;
    Dialog dialogSelectMethod;
    String imageFilePath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PICK_IMAGE = 100;
    private static final int STATE_READ_EXTERNAL_STORAGE = 7;
    private static final int STATE_WRITE_EXTERNAL_STORAGE = 6;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STATE_WRITE_EXTERNAL_STORAGE);
        }

        reqPhonePermission();

        progressDialog = new ProgressDialog(RegistrasiActivity.this);

        sharedPreference = new SharedPreference(RegistrasiActivity.this);

        textViewNomorTelepon = findViewById(R.id.tv_nomor_telepon);
        imageViewProfil = findViewById(R.id.image_view_profil);
        editTextNama = findViewById(R.id.et_nama);
        editTextEmail = findViewById(R.id.et_email);
        editTextAlamat = findViewById(R.id.et_alamat);
        editTextPassword = findViewById(R.id.et_password);
        layoutNama = findViewById(R.id.layout_nama);
        layoutEmail = findViewById(R.id.layout_email);
        layoutAlamat = findViewById(R.id.layout_alamat);
        layoutPassword = findViewById(R.id.layout_password);
        //progressBar = findViewById(R.id.progres_bar);

        Intent intent = getIntent();
        textViewNomorTelepon.setText(intent.getStringExtra(getString(R.string.nomor_telepon)));

        dialogSelectMethod = new Dialog(RegistrasiActivity.this);
        dialogSelectMethod.setContentView(R.layout.dialog_pilih_photo);
        
        buttonNext = findViewById(R.id.btn_next);

        buttonNext.setOnClickListener(this);
        imageViewProfil.setOnClickListener(this);
    }

    private void reqPhonePermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_STATE_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},PHONE_STATE_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STATE_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                if (checkField())
                    getData();
                break;
            case R.id.image_view_profil:
                if (ContextCompat.checkSelfPermission(RegistrasiActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(RegistrasiActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STATE_READ_EXTERNAL_STORAGE);
                } else if (ContextCompat.checkSelfPermission(RegistrasiActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(RegistrasiActivity.this,new String[]{Manifest.permission.CAMERA}, CAMERA_STATE_CODE);
                } else {
                    showPopUpChoosePhoto();
                }
                break;
        }
    }

    private boolean checkField() {

        boolean status = true;

        if (Objects.requireNonNull(editTextNama.getText()).toString().trim().isEmpty()){
            layoutNama.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutNama.setError(null);
        }

        if (Objects.requireNonNull(editTextEmail.getText()).toString().trim().isEmpty()){
            layoutEmail.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (!Objects.requireNonNull(editTextEmail.getText()).toString().trim().matches(emailPattern)){
            layoutEmail.setError(getString(R.string.email_tidak_valid));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutEmail.setError(null);
        }

        if (Objects.requireNonNull(editTextAlamat.getText()).toString().trim().isEmpty()){
            layoutAlamat.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutAlamat.setError(null);
        }

        if (Objects.requireNonNull(editTextPassword.getText()).toString().trim().isEmpty()){
            layoutPassword.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else if (editTextPassword.getText().toString().length() < 6) {
            layoutPassword.setError(getString(R.string.password_pendek));
            showSnackBarFieldEmpty();
            status = false;
        } else if (cekPassword(editTextPassword.getText().toString().toLowerCase())
        ) {
            layoutPassword.setError(getString(R.string.password_salah));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutPassword.setError(null);
        }

        if (imageUri == null){
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.harus_memilih_foto), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .setActionTextColor(getResources().getColor(R.color.white))
                    .show();
            status = false;
        }

        return status;
    }

    private boolean cekPassword(String string){
        boolean status = true;
        if (
                (
                        string.contains("a") ||
                                string.contains("b") ||
                                string.contains("c") ||
                                string.contains("d") ||
                                string.contains("e") ||
                                string.contains("f") ||
                                string.contains("g") ||
                                string.contains("h") ||
                                string.contains("i") ||
                                string.contains("j") ||
                                string.contains("k") ||
                                string.contains("l") ||
                                string.contains("m") ||
                                string.contains("n") ||
                                string.contains("o") ||
                                string.contains("p") ||
                                string.contains("q") ||
                                string.contains("r") ||
                                string.contains("s") ||
                                string.contains("t") ||
                                string.contains("u") ||
                                string.contains("v") ||
                                string.contains("w") ||
                                string.contains("x") ||
                                string.contains("y") ||
                                string.contains("z")
                ) && (
                        string.contains("0") ||
                                string.contains("1") ||
                                string.contains("2") ||
                                string.contains("3") ||
                                string.contains("4") ||
                                string.contains("5") ||
                                string.contains("6") ||
                                string.contains("7") ||
                                string.contains("8") ||
                                string.contains("9")
                )
        ){
            status = false;
        }
        return status;
    }

    private void showSnackBarFieldEmpty(){
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.field_kosong), Snackbar.LENGTH_LONG)
                .setBackgroundTint(getResources().getColor(R.color.red))
                .setActionTextColor(getResources().getColor(R.color.white))
                .show();
    }

    private void updateUI() {
        startActivity(new Intent(RegistrasiActivity.this, BerandaActivity.class));
        finish();
    }

    private void showPopUpChoosePhoto() {
        dialogCardViewGalery = dialogSelectMethod.findViewById(R.id.cv_gallery);
        dialogCardViewCamera = dialogSelectMethod.findViewById(R.id.cv_camera);

        dialogCardViewGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elapseClick();

                dialogSelectMethod.dismiss();
                openGallery();
            }
        });

        dialogCardViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                elapseClick();

                dialogSelectMethod.dismiss();
                openCameraIntent();
            }
        });

        dialogSelectMethod.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSelectMethod.getWindow().setGravity(Gravity.BOTTOM);
        dialogSelectMethod.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSelectMethod.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        dialogSelectMethod.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();

            imageViewProfil.setImageURI(imageUri);
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            File f = new File(imageFilePath);
            imageUri = Uri.fromFile(f);

            imageViewProfil.setImageURI(imageUri);
        }
    }

    private void openGallery() {

        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, PICK_IMAGE);

//        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(gallery, PICK_IMAGE);
        /*
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        @SuppressLint("IntentReset") Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(gallery, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);*/
    }

    private void openCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(RegistrasiActivity.this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(RegistrasiActivity.this,
                        RegistrasiActivity.this.getPackageName(),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = RegistrasiActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STATE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(RegistrasiActivity.this, getString(R.string.izin_disetujui), Toast.LENGTH_SHORT).show();
                //showPopUpChoosePhoto();
            } else {
                Toast.makeText(RegistrasiActivity.this, getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void getData() {

        //buttonNext.setVisibility(View.GONE);
        //progressBar.setVisibility(View.VISIBLE);

        String filePath = RealPathUtil.getRealPath(RegistrasiActivity.this, imageUri);
        File file = new File(filePath);

        if (file.exists()){
            Log.d("File nama",""+"file exist");
        }

        Log.d("File nama",""+file.getName());
        Log.d("File nama",""+filePath);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

//        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file); //membungkus file ke dalam request body
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file); //membungkus file ke dalam request body
        MultipartBody.Part filegambar = MultipartBody.Part.createFormData("filegambar", file.getName(), mFile); // membuat formdata multipart berisi request body

        RequestBody requestBodyNama = RequestBody.create(MediaType.parse("text/plain"), editTextNama.getText().toString().trim());
        RequestBody requestBodyNomorTelepon = RequestBody.create(MediaType.parse("text/plain"), textViewNomorTelepon.getText().toString().trim());
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("text/plain"), editTextPassword.getText().toString().trim());
        RequestBody requestBodyAlamat = RequestBody.create(MediaType.parse("text/plain"), editTextAlamat.getText().toString().trim());
        RequestBody requestBodyEmail = RequestBody.create(MediaType.parse("text/plain"), editTextEmail.getText().toString().trim());
        RequestBody requestBodyImei = RequestBody.create(MediaType.parse("text/plain"), DeviceIDUtil.getUniqueIMEIId(RegistrasiActivity.this));

        Call<RegisterResponse> call = apiInterface.registerUser(
                filegambar,
                requestBodyNama,
                requestBodyNomorTelepon,
                requestBodyPassword,
                requestBodyAlamat,
                requestBodyEmail,
                requestBodyImei
        );

        setDisableAll();

        call.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {

                setEnableAll();
                //buttonNext.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (!response.body().getError()){
                        RegisterResponse registerResponse = response.body();
                        SharedPreference.setLogged(true);
                        SharedPreference.setIdUser(RegistrasiActivity.this, registerResponse.getId());
                        SharedPreference.setNamaUser(RegistrasiActivity.this, registerResponse.getUsername());
                        SharedPreference.setNomorTelepon(RegistrasiActivity.this, registerResponse.getNoHp());
                        SharedPreference.setAlamat(RegistrasiActivity.this, registerResponse.getAlamat());
                        SharedPreference.setEmail(RegistrasiActivity.this, registerResponse.getEmail());
                        SharedPreference.setJwtUser(RegistrasiActivity.this, registerResponse.getJwt());
                        SharedPreference.setFotoProfil(RegistrasiActivity.this, registerResponse.getPhoto());
                        updateUI();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.register_gagal),
                                Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("coment", "onUnResponse");

                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.terjadi_kesalahan),
                            Snackbar.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                //buttonNext.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                setEnableAll();

                Snackbar.make(findViewById(android.R.id.content), getString(R.string.server_error),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void setDisableAll(){
        progressDialog.setTitle(getString(R.string.submit_data));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void setEnableAll(){
        progressDialog.dismiss();
    }
}