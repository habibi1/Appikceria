package com.bkipmlampung.appikceria.ui.home.pengaduan;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.bkipmlampung.appikceria.utils.StringUtils;
import com.bkipmlampung.appikceria.utils.TimeUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
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

public class PengaduanActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardView;

    TextInputEditText editTextNama, editTextAlamat, editTextInstansi,
        editTextTujuanPengaduan, editTextSumberInformasi, editTextIsiAduan;

    AutoCompleteTextView dropdownBidangPelayanan;

    TextView textViewLampiran;

    TextInputLayout layoutNama, layoutAlamat, layoutInstansi, layoutBidangPelayanan,
            layoutTujuanPengaduan, layoutSumberInformasi, layoutIsiAduan;

    SwitchMaterial switchMaterial;

    ImageView imageViewLampiran;

    Button buttonNext;
    
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaduan);

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, STATE_CAMERA_CODE);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(PengaduanActivity.this);

        dialogSelectMethod = new Dialog(PengaduanActivity.this);
        dialogSelectMethod.setContentView(R.layout.dialog_pilih_photo);

        cardView = findViewById(R.id.card_view_lampiran);
        textViewLampiran = findViewById(R.id.text_view_lampiran);
        imageViewLampiran = findViewById(R.id.image_view_lampiran);
        
        //progressBar = findViewById(R.id.progres_bar);

        editTextNama = findViewById(R.id.et_nama);
        editTextAlamat = findViewById(R.id.et_alamat);
        editTextInstansi = findViewById(R.id.et_instansi);
        dropdownBidangPelayanan = findViewById(R.id.act_bidang_pelayanan);
        editTextTujuanPengaduan = findViewById(R.id.et_tujuan_pengaduan);
        editTextSumberInformasi = findViewById(R.id.et_sumber_informasi);
        editTextIsiAduan = findViewById(R.id.et_isi_aduan);

        editTextNama.setText(SharedPreference.getNamaUser(PengaduanActivity.this));
        editTextAlamat.setText(SharedPreference.getAlamat(PengaduanActivity.this));

        layoutNama = findViewById(R.id.layout_nama);
        layoutAlamat = findViewById(R.id.layout_alamat);
        layoutInstansi = findViewById(R.id.layout_instansi);
        layoutBidangPelayanan = findViewById(R.id.layout_bidang_pelayanan);
        layoutTujuanPengaduan = findViewById(R.id.layout_tujuan_pengaduan);
        layoutSumberInformasi = findViewById(R.id.layout_sumber_informasi);
        layoutIsiAduan = findViewById(R.id.layout_isi_aduan);

        buttonNext = findViewById(R.id.btn_submit);

        switchMaterial = findViewById(R.id.switch_lampiran);

        String[] BIDANG_PELAYANAN = StringUtils.getArrayFromMenu(PengaduanActivity.this, R.menu.bidang_pelayanan_menu);

        ArrayAdapter<String> adapterBidangPelayanan =
                new ArrayAdapter<>(
                        PengaduanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        BIDANG_PELAYANAN);

        dropdownBidangPelayanan.setText(getString(R.string.pilih));
        dropdownBidangPelayanan.setAdapter(adapterBidangPelayanan);

        // To listen for a switch's checked/unchecked state changes
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    cardView.setVisibility(View.VISIBLE);
                else
                    cardView.setVisibility(View.GONE);
            }
        });
        // Responds to switch being checked/unchecked
        
        buttonNext.setOnClickListener(this);
        cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if (checkField())
                    if (switchMaterial.isChecked())
                        getData();
                    else
                        getData2();
                break;
            case R.id.card_view_lampiran:
                if (ContextCompat.checkSelfPermission(PengaduanActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PengaduanActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STATE_READ_EXTERNAL_STORAGE);
                } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, STATE_CAMERA_CODE);
                } else {
                    openCameraIntent();
                    //showPopUpChoosePhoto();
                }
                break;
        }
    }

    private boolean checkField() {

        boolean status = true;

        if (switchMaterial.isChecked()){
            if (imageUri==null){
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.harus_memilih_foto), Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getResources().getColor(R.color.red))
                        .setActionTextColor(getResources().getColor(R.color.white))
                        .show();
                status = false;
            }
        }

        if (Objects.requireNonNull(editTextNama.getText()).toString().trim().isEmpty()){
            layoutNama.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutNama.setError(null);
        }

        if (Objects.requireNonNull(editTextAlamat.getText()).toString().trim().isEmpty()){
            layoutAlamat.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutAlamat.setError(null);
        }

        if (Objects.requireNonNull(editTextInstansi.getText()).toString().trim().isEmpty()){
            layoutInstansi.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutInstansi.setError(null);
        }

        if (Objects.requireNonNull(dropdownBidangPelayanan.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutBidangPelayanan.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutBidangPelayanan.setError(null);
        }

        if (Objects.requireNonNull(editTextTujuanPengaduan.getText()).toString().trim().isEmpty()){
            layoutTujuanPengaduan.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutTujuanPengaduan.setError(null);
        }

        if (Objects.requireNonNull(editTextSumberInformasi.getText()).toString().trim().isEmpty()){
            layoutSumberInformasi.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSumberInformasi.setError(null);
        }

        if (Objects.requireNonNull(editTextIsiAduan.getText()).toString().trim().isEmpty()){
            layoutIsiAduan.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutIsiAduan.setError(null);
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
        if (takePictureIntent.resolveActivity(PengaduanActivity.this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(PengaduanActivity.this,
                        PengaduanActivity.this.getPackageName(),
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
        File storageDir = PengaduanActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
                Toast.makeText(PengaduanActivity.this, getString(R.string.izin_disetujui), Toast.LENGTH_SHORT).show();
                showPopUpChoosePhoto();
            } else {
                Toast.makeText(PengaduanActivity.this, getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
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

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        MultipartBody.Part filegambar;

        String filePath = getRealPathFromURIPath(imageUri, PengaduanActivity.this);
        final File file = new File(filePath);
        Log.d("File",""+file.getName());

        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file); //membungkus file ke dalam request body
        filegambar = MultipartBody.Part.createFormData("filegambar", file.getName(), mFile); // membuat formdata multipart berisi request body

        RequestBody requestBodyIdUser = RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getIdUser(PengaduanActivity.this));
        RequestBody requestBodyJwtUser = RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getJwtUser(PengaduanActivity.this));
        RequestBody requestBodyNama = RequestBody.create(MediaType.parse("text/plain"), editTextNama.getText().toString().trim());
        RequestBody requestBodyTanggal = RequestBody.create(MediaType.parse("text/plain"), TimeUtil.getTanggal());
        RequestBody requestBodyAlamat = RequestBody.create(MediaType.parse("text/plain"), editTextAlamat.getText().toString().trim());
        RequestBody requestBodyPekerjaanI = RequestBody.create(MediaType.parse("text/plain"), editTextInstansi.getText().toString().trim());
        RequestBody requestBodyNoHp = RequestBody.create(MediaType.parse("text/plain"), "dsdsds");
        RequestBody requestBodyBidangPelayanan = RequestBody.create(MediaType.parse("text/plain"), dropdownBidangPelayanan.getText().toString().trim());
        RequestBody requestBodyTujuanPengaduan = RequestBody.create(MediaType.parse("text/plain"), editTextTujuanPengaduan.getText().toString().trim());
        RequestBody requestBodySumberInformasi = RequestBody.create(MediaType.parse("text/plain"), editTextSumberInformasi.getText().toString());
        RequestBody requestBodyIsiAduan = RequestBody.create(MediaType.parse("text/plain"), editTextIsiAduan.getText().toString().trim());
        RequestBody requestBodyKonfirmasi = RequestBody.create(MediaType.parse("text/plain"), getString(R.string.menunggu));

        Call<SetDataImagesResponse> call = apiInterface.setDataPengaduanLayanan(
                filegambar,
                requestBodyIdUser,
                requestBodyJwtUser,
                requestBodyNama,
                requestBodyTanggal,
                requestBodyAlamat,
                requestBodyPekerjaanI,
                requestBodyNoHp,
                requestBodyBidangPelayanan,
                requestBodyTujuanPengaduan,
                requestBodySumberInformasi,
                requestBodyIsiAduan,
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

    private void getData2() {

        //buttonNext.setVisibility(View.GONE);
        //progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<SetDataImagesResponse> call = apiInterface.setDataPengaduanLayanan2(
                SharedPreference.getIdUser(PengaduanActivity.this),
                SharedPreference.getJwtUser(PengaduanActivity.this),
                editTextNama.getText().toString().trim(),
                TimeUtil.getTanggal(),
                editTextAlamat.getText().toString().trim(),
                editTextInstansi.getText().toString().trim(),
                SharedPreference.getNomorTelepon(PengaduanActivity.this),
                dropdownBidangPelayanan.getText().toString().trim(),
                editTextTujuanPengaduan.getText().toString().trim(),
                editTextSumberInformasi.getText().toString(),
                editTextIsiAduan.getText().toString().trim(),
                getString(R.string.menunggu));

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
        Toast.makeText(PengaduanActivity.this, message, Toast.LENGTH_SHORT).show();
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