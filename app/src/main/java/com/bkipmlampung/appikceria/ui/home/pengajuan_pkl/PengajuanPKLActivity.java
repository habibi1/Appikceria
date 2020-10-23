package com.bkipmlampung.appikceria.ui.home.pengajuan_pkl;

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
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.SetDataImagesResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.SharedPreference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.bkipmlampung.appikceria.utils.ControlUtil.elapseClick;

public class PengajuanPKLActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardViewLampiran;

    TextInputEditText editTextNama, editTextTanggalLahir, editTextTempatLahir,
        editTextSekolahUniversitas, editTextJurusan;

    TextView textViewLampiran;

    ImageView imageViewLampiran;

    TextInputLayout layoutNama, layoutTanggalLahir, layoutTempatLahir,
            layoutSekolahUniversitas, layoutJurusan;

    MaterialButton buttonNext;

    //ProgressBar progressBar;

    ProgressDialog progressDialog;

    CardView dialogCardViewCamera, dialogCardViewGalery;
    Dialog dialogSelectMethod;
    String imageFilePath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PICK_IMAGE = 100;
    private static final int STATE_READ_EXTERNAL_STORAGE = 7;
    private static final int STATE_CAMERA_CODE = 3;
    Uri imageUri = null;

    Calendar calendar;
    long today;
    MaterialDatePicker materialDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_p_k_l);

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, STATE_CAMERA_CODE);
        }

        progressDialog = new ProgressDialog(PengajuanPKLActivity.this);

        editTextNama = findViewById(R.id.et_nama);
        editTextTanggalLahir = findViewById(R.id.et_tanggal_lahir);
        editTextTempatLahir = findViewById(R.id.et_tempat_lahir);
        editTextSekolahUniversitas = findViewById(R.id.et_sekolah_universitas);
        editTextJurusan = findViewById(R.id.et_jurusan);
        textViewLampiran = findViewById(R.id.text_view_lampiran);

        imageViewLampiran = findViewById(R.id.image_view_lampiran);
        imageViewLampiran.setVisibility(View.GONE);

        editTextNama.setText(SharedPreference.getNamaUser(PengajuanPKLActivity.this));

        layoutNama = findViewById(R.id.layout_nama);
        layoutTanggalLahir = findViewById(R.id.layout_tanggal_lahir);
        layoutTempatLahir = findViewById(R.id.layout_tempat_lahir);
        layoutSekolahUniversitas = findViewById(R.id.layout_sekolah_universitas);
        layoutJurusan = findViewById(R.id.layout_jurusan);

        //progressBar = findViewById(R.id.progres_bar);

        dialogSelectMethod = new Dialog(PengajuanPKLActivity.this);
        dialogSelectMethod.setContentView(R.layout.dialog_pilih_photo);

        cardViewLampiran = findViewById(R.id.card_view_lampiran);

        cardViewLampiran.setOnClickListener(this);

        buttonNext = findViewById(R.id.btn_submit);

        buttonNext.setOnClickListener(this);

        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        today = MaterialDatePicker.todayInUtcMilliseconds();

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Pilih tanggal");
        builder.setSelection(today);
        materialDatePicker = builder.build();

        editTextTanggalLahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                }
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                editTextTanggalLahir.setText(materialDatePicker.getHeaderText());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if (checkField())
                    getData();
                break;
            case R.id.card_view_lampiran:
                if (ContextCompat.checkSelfPermission(PengajuanPKLActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PengajuanPKLActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STATE_READ_EXTERNAL_STORAGE);
                } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, STATE_CAMERA_CODE);
                } else {
                    openCameraIntent();
                    //showPopUpChoosePhoto();
                }
                break;
            case R.id.et_tanggal_lahir:
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
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

        if (Objects.requireNonNull(editTextTanggalLahir.getText()).toString().trim().isEmpty()){
            layoutTanggalLahir.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutTanggalLahir.setError(null);
        }

        if (Objects.requireNonNull(editTextTempatLahir.getText()).toString().trim().isEmpty()){
            layoutTempatLahir.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutTempatLahir.setError(null);
        }

        if (Objects.requireNonNull(editTextSekolahUniversitas.getText()).toString().trim().isEmpty()){
            layoutSekolahUniversitas.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSekolahUniversitas.setError(null);
        }

        if (Objects.requireNonNull(editTextJurusan.getText()).toString().trim().isEmpty()){
            layoutJurusan.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutJurusan.setError(null);
        }

        if (imageUri==null){
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.harus_memilih_foto), Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .setActionTextColor(getResources().getColor(R.color.white))
                    .show();
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
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            File f = new File(imageFilePath);
            imageUri = Uri.fromFile(f);
        }

        if (imageUri != null){
            textViewLampiran.setVisibility(View.GONE);

            imageViewLampiran.setImageURI(imageUri);
            imageViewLampiran.setVisibility(View.VISIBLE);
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void openCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(PengajuanPKLActivity.this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(PengajuanPKLActivity.this,
                        PengajuanPKLActivity.this.getPackageName(),
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
        File storageDir = PengajuanPKLActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
                Toast.makeText(PengajuanPKLActivity.this, getString(R.string.izin_disetujui), Toast.LENGTH_SHORT).show();
                showPopUpChoosePhoto();
            } else {
                Toast.makeText(PengajuanPKLActivity.this, getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            return cursor.getString(idx);
        }
    }

    private void getData() {

        //buttonNext.setVisibility(View.GONE);
        //progressBar.setVisibility(View.VISIBLE);

        String filePath = getRealPathFromURIPath(imageUri, PengajuanPKLActivity.this);
        final File file = new File(filePath);
        Log.d("File",""+file.getName());

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file); //membungkus file ke dalam request body
        MultipartBody.Part filegambar = MultipartBody.Part.createFormData("filegambar", file.getName(), mFile); // membuat formdata multipart berisi request body

        RequestBody requestBodyIdUser = RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getIdUser(PengajuanPKLActivity.this));
        RequestBody requestBodyJwtUser = RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getJwtUser(PengajuanPKLActivity.this));
        RequestBody requestBodyNama = RequestBody.create(MediaType.parse("text/plain"), editTextNama.getText().toString().trim());
        RequestBody requestBodyTempatLahir = RequestBody.create(MediaType.parse("text/plain"), editTextTempatLahir.getText().toString().trim());
        RequestBody requestBodyTahunLahir = RequestBody.create(MediaType.parse("text/plain"), editTextTanggalLahir.getText().toString().trim());
        RequestBody requestBodyNamaUniv = RequestBody.create(MediaType.parse("text/plain"), editTextSekolahUniversitas.getText().toString().trim());
        RequestBody requestBodyNamaJur = RequestBody.create(MediaType.parse("text/plain"), editTextJurusan.getText().toString());
        RequestBody requestBodyNilaiPkl = RequestBody.create(MediaType.parse("text/plain"), "-");
        RequestBody requestBodyKonfirmasi = RequestBody.create(MediaType.parse("text/plain"), getString(R.string.menunggu));

        Call<SetDataImagesResponse> call = apiInterface.setDataPengajuanPkl(
                filegambar,
                requestBodyIdUser,
                requestBodyJwtUser,
                requestBodyNama,
                requestBodyTempatLahir,
                requestBodyTahunLahir,
                requestBodyNamaUniv,
                requestBodyNamaJur,
                requestBodyNilaiPkl,
                requestBodyKonfirmasi);

        setDisableAll();

        call.enqueue(new Callback<SetDataImagesResponse>() {
            @Override
            public void onResponse(Call<SetDataImagesResponse> call, retrofit2.Response<SetDataImagesResponse> response) {

                setEnableAll();
                //buttonNext.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    if (response.body().getStatus()) {
                        Log.d("coment", "isSuccesful");
                        showToast(getString(R.string.submit_data_berhasil));
                        finish();
                    } else {
                        Log.d("coment", "onUnResponse");
                        showToast(getString(R.string.terjadi_kesalahan));
                    }

                } else {
                    Log.d("coment", "onUnResponse");

                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.terjadi_kesalahan),
                            Snackbar.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SetDataImagesResponse> call, Throwable t) {
                setEnableAll();
                Log.d("coment", "onFailed");
                //buttonNext.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);

                Snackbar.make(findViewById(android.R.id.content), getString(R.string.server_error),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void showToast(String message){
        Toast.makeText(PengajuanPKLActivity.this, message, Toast.LENGTH_SHORT).show();
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