package com.bkipmlampung.appikceria.ui.profil;

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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bkipmlampung.appikceria.BuildConfig;
import com.bkipmlampung.appikceria.InputTeleponActivity;
import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.EditProfilResponse;
import com.bkipmlampung.appikceria.model.LogoutResponse;
import com.bkipmlampung.appikceria.model.UpdateProfileResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.DeviceIDUtil;
import com.bkipmlampung.appikceria.utils.RealPathUtil;
import com.bkipmlampung.appikceria.utils.SharedPreference;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jsibbold.zoomage.ZoomageView;

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

import static android.app.Activity.RESULT_OK;
import static com.bkipmlampung.appikceria.utils.ControlUtil.elapseClick;

public class ProfilFragment extends Fragment implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    Dialog dialogZoomable;
    ZoomageView zoomageView;
    ImageView dialogZoomableClose;

    TextInputEditText editTextNama, editTextEmail, editTextAlamat,
        dialogEditTextNama, dialogEditTextEmail, dialogEditTextAlamat,
        dialogEditTextPasswordLama, dialogEditTextPasswordBaru, dialogEditTextKonfirmasiPasswordBaru;
    TextInputLayout dialogLayoutNama, dialogLayoutEmail, dialogLayoutAlamat, dialogLayoutPasswordLama,
            dialogLayoutPasswordBaru, dialogLayoutKonfirmasiPasswordBaru;
    TextView textViewNomorTelepon;
    ImageView imageViewProfil, dialogImageViewProfil, dialogDismiss, dialogDismisPassword;
    
    //ProgressBar dialogEditProfilProgressBar, dialogPasswordProgressBar;

    ProgressDialog progressDialog;

    Dialog dialogEditProfil, dialogEditPassword;

    Button buttonLogout, buttonAbout, dialogButtonEdit, buttonEditPassword, dialogButtonKonfirmasi;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    String number = "[0-9]+";
    String carakter = "[a-zA-Z]+";

    CardView dialogCardViewCamera, dialogCardViewGalery;
    Dialog dialogSelectMethod;
    String imageFilePath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PICK_IMAGE = 100;
    private static final int STATE_READ_EXTERNAL_STORAGE = 7;
    private static final int STATE_WRITE_EXTERNAL_STORAGE = 6;
    private static final int STATE_CAMERA_CODE = 3;
    Uri imageUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STATE_WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, STATE_CAMERA_CODE);
        }

        Toolbar toolbar = root.findViewById(R.id.topAppBar);
        toolbar.setOnMenuItemClickListener(this);

        editTextNama = root.findViewById(R.id.et_nama);
        editTextEmail = root.findViewById(R.id.et_email);
        editTextAlamat = root.findViewById(R.id.et_alamat);
        imageViewProfil = root.findViewById(R.id.image_view_profil);
        textViewNomorTelepon = root.findViewById(R.id.tv_nomor_telepon);
        buttonLogout = root.findViewById(R.id.btn_logout);
        buttonAbout = root.findViewById(R.id.btn_about);
        buttonEditPassword = root.findViewById(R.id.btn_edit_password);

        progressDialog = new ProgressDialog(getActivity());

        dialogSelectMethod = new Dialog(getActivity());
        dialogSelectMethod.setContentView(R.layout.dialog_pilih_photo);

        dialogEditProfil = new Dialog(getActivity());
        dialogEditProfil.setContentView(R.layout.dialog_edit_profil);

        dialogZoomable = new Dialog(getActivity());
        dialogZoomable.setContentView(R.layout.dialog_zoomable);

        zoomageView = dialogZoomable.findViewById(R.id.zoomableView);
        dialogZoomableClose = dialogZoomable.findViewById(R.id.iv_dismiss);

        dialogEditPassword = new Dialog(getActivity());
        dialogEditPassword.setContentView(R.layout.dialog_edit_password);

        dialogEditTextPasswordLama = dialogEditPassword.findViewById(R.id.et_password_lama);
        dialogEditTextPasswordBaru = dialogEditPassword.findViewById(R.id.et_password_baru);
        dialogEditTextKonfirmasiPasswordBaru = dialogEditPassword.findViewById(R.id.et_konfirmasi_password_baru);
        dialogDismisPassword = dialogEditPassword.findViewById(R.id.iv_dismiss);
        dialogLayoutPasswordLama = dialogEditPassword.findViewById(R.id.layout_password_lama);
        dialogLayoutPasswordBaru = dialogEditPassword.findViewById(R.id.layout_password_baru);
        dialogLayoutKonfirmasiPasswordBaru = dialogEditPassword.findViewById(R.id.layout_konfirmasi_password_baru);
        dialogDismisPassword = dialogEditPassword.findViewById(R.id.iv_dismiss);
        dialogButtonKonfirmasi = dialogEditPassword.findViewById(R.id.btn_konfirmasi_password);

        dialogLayoutNama = dialogEditProfil.findViewById(R.id.layout_nama);
        dialogLayoutEmail = dialogEditProfil.findViewById(R.id.layout_email);
        dialogLayoutAlamat = dialogEditProfil.findViewById(R.id.layout_alamat);

        dialogEditTextNama = dialogEditProfil.findViewById(R.id.et_nama);
        dialogEditTextEmail = dialogEditProfil.findViewById(R.id.et_email);
        dialogEditTextAlamat = dialogEditProfil.findViewById(R.id.et_alamat);
        dialogImageViewProfil = dialogEditProfil.findViewById(R.id.image_view_profil_dialog);
        dialogDismiss = dialogEditProfil.findViewById(R.id.iv_dismiss);
        dialogButtonEdit = dialogEditProfil.findViewById(R.id.btn_edit);
        //dialogEditProfilProgressBar = dialogEditProfil.findViewById(R.id.progres_bar);
        //dialogPasswordProgressBar = dialogEditPassword.findViewById(R.id.progres_bar);

        dialogLayoutNama = dialogEditProfil.findViewById(R.id.layout_nama);
        dialogLayoutEmail = dialogEditProfil.findViewById(R.id.layout_email);
        dialogLayoutAlamat = dialogEditProfil.findViewById(R.id.layout_alamat);

        editTextNama.setText(SharedPreference.getNamaUser(getActivity()));
        editTextEmail.setText(SharedPreference.getEmail(getActivity()));
        editTextAlamat.setText(SharedPreference.getAlamat(getActivity()));
        textViewNomorTelepon.setText(SharedPreference.getNomorTelepon(getActivity()));

        dialogEditTextNama.setText(SharedPreference.getNamaUser(getActivity()));
        dialogEditTextEmail.setText(SharedPreference.getEmail(getActivity()));
        dialogEditTextAlamat.setText(SharedPreference.getAlamat(getActivity()));

        Glide.with(getActivity())
                .load(BuildConfig.BASE_URL_APP + SharedPreference.getFotoProfil(getActivity()))
                .centerCrop()
                .into(imageViewProfil);

        Glide.with(getActivity())
                .load(BuildConfig.BASE_URL_APP + SharedPreference.getFotoProfil(getActivity()))
                .centerCrop()
                .into(dialogImageViewProfil);

        Glide.with(getActivity())
                .load(BuildConfig.BASE_URL_APP + SharedPreference.getFotoProfil(getActivity()))
                .into(zoomageView);

        buttonLogout.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        dialogDismiss.setOnClickListener(this);
        dialogDismisPassword.setOnClickListener(this);
        dialogButtonEdit.setOnClickListener(this);
        dialogButtonKonfirmasi.setOnClickListener(this);
        buttonEditPassword.setOnClickListener(this);
        dialogImageViewProfil.setOnClickListener(this);
        imageViewProfil.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                getDataLogout();
                break;
            case R.id.btn_about:
                startActivity(new Intent(getActivity(), TentangActivity.class));
                break;
            case R.id.iv_dismiss:
                dialogEditProfil.dismiss();
                dialogEditPassword.dismiss();
                break;
            case R.id.btn_edit:
                if (checkField()){
                    if (imageUri == null)
                        getData();
                    else
                        getDataImage();
                }
                break;
            case R.id.image_view_profil_dialog:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STATE_READ_EXTERNAL_STORAGE);
                } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, STATE_CAMERA_CODE);
                } else {
                    openCameraIntent();
                    //showPopUpChoosePhoto();
                }
                break;
            case R.id.btn_edit_password:
                dialogEditTextPasswordLama.setText("");
                dialogEditTextKonfirmasiPasswordBaru.setText("");
                dialogEditTextPasswordBaru.setText("");
                Objects.requireNonNull(dialogEditPassword.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogEditPassword.getWindow().setGravity(Gravity.CENTER);
                dialogEditPassword.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogEditPassword.setCanceledOnTouchOutside(false);
                dialogEditPassword.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
                dialogEditPassword.show();
                break;
            case R.id.btn_konfirmasi_password:
                if (checkFieldPassword())
                    getDataPassword();
                break;
            case R.id.image_view_profil:
                showZoomable();
                break;
        }
    }

    private void showZoomable(){

        dialogZoomableClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogZoomable.dismiss();
            }
        });

        Objects.requireNonNull(dialogZoomable.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogZoomable.getWindow().setGravity(Gravity.CENTER);
        dialogZoomable.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogZoomable.setCanceledOnTouchOutside(false);
        dialogZoomable.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        dialogZoomable.show();
    }

    private boolean checkField() {

        boolean status = true;

        if (Objects.requireNonNull(dialogEditTextNama.getText()).toString().trim().isEmpty()){
            dialogLayoutNama.setError(getString(R.string.harus_diisi));
            status = false;
        } else {
            dialogLayoutNama.setError(null);
        }

        if (Objects.requireNonNull(dialogEditTextEmail.getText()).toString().trim().isEmpty()){
            dialogLayoutEmail.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (!Objects.requireNonNull(dialogEditTextEmail.getText()).toString().trim().matches(emailPattern)){
            dialogLayoutEmail.setError(getString(R.string.email_tidak_valid));
            status = false;
        } else {
            dialogLayoutEmail.setError(null);
        }

        if (Objects.requireNonNull(dialogEditTextAlamat.getText()).toString().trim().isEmpty()){
            dialogLayoutAlamat.setError(getString(R.string.harus_diisi));
            status = false;
        } else {
            dialogLayoutAlamat.setError(null);
        }

        return status;
    }

    private boolean checkFieldPassword() {

        boolean status = true;

        if (Objects.requireNonNull(dialogEditTextPasswordLama.getText()).toString().trim().isEmpty()){
            dialogLayoutPasswordLama.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (dialogEditTextPasswordLama.getText().toString().length() < 6) {
            dialogLayoutPasswordLama.setError(getString(R.string.password_pendek));
            status = false;
        } else if (cekPassword(dialogEditTextPasswordLama.getText().toString().toLowerCase())) {
            dialogLayoutPasswordLama.setError(getString(R.string.password_salah));
            status = false;
        } else {
            dialogLayoutPasswordLama.setError(null);
        }

        if (Objects.requireNonNull(dialogEditTextPasswordBaru.getText()).toString().trim().isEmpty()){
            dialogLayoutPasswordBaru.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (Objects.requireNonNull(dialogEditTextPasswordBaru.getText()).toString().trim().equals(dialogEditTextPasswordLama.getText().toString().trim())){
            dialogLayoutPasswordBaru.setError(getString(R.string.password_sama));
            status = false;
        } else if (dialogEditTextPasswordBaru.getText().toString().length() < 6) {
            dialogLayoutPasswordBaru.setError(getString(R.string.password_pendek));
            status = false;
        } else if (cekPassword(dialogEditTextPasswordBaru.getText().toString().toLowerCase())) {
            dialogLayoutPasswordBaru.setError(getString(R.string.password_salah));
            status = false;
        } else {
            dialogLayoutPasswordBaru.setError(null);
        }

        if (dialogEditTextKonfirmasiPasswordBaru.getText().toString().trim().isEmpty()){
            dialogLayoutKonfirmasiPasswordBaru.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (!Objects.requireNonNull(dialogEditTextKonfirmasiPasswordBaru.getText()).toString().trim().equals(dialogEditTextPasswordBaru.getText().toString().trim())){
            dialogLayoutKonfirmasiPasswordBaru.setError(getString(R.string.password_tidak_sama));
            status = false;
        } else if (dialogEditTextKonfirmasiPasswordBaru.getText().toString().length() < 6) {
            dialogLayoutKonfirmasiPasswordBaru.setError(getString(R.string.password_pendek));
            status = false;
        } else if (cekPassword(dialogEditTextKonfirmasiPasswordBaru.getText().toString().toLowerCase())) {
            dialogLayoutKonfirmasiPasswordBaru.setError(getString(R.string.password_salah));
            status = false;
        } else {
            dialogLayoutKonfirmasiPasswordBaru.setError(null);
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Log.d("EditView", "Current");
                Objects.requireNonNull(dialogEditProfil.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogEditProfil.getWindow().setGravity(Gravity.CENTER);
                dialogEditProfil.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogEditProfil.setCanceledOnTouchOutside(false);
                dialogEditProfil.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
                dialogEditProfil.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

            dialogImageViewProfil.setImageURI(imageUri);
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            File f = new File(imageFilePath);
            imageUri = Uri.fromFile(f);

            dialogImageViewProfil.setImageURI(imageUri);
        }
    }

    private void openGallery() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, PICK_IMAGE);

    }

    private void openCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        getActivity().getPackageName(),
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
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
                Toast.makeText(getActivity(), getString(R.string.izin_disetujui), Toast.LENGTH_SHORT).show();
                //showPopUpChoosePhoto();
            } else {
                Toast.makeText(getActivity(), getString(R.string.izin_ditolak), Toast.LENGTH_SHORT).show();
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

        //dialogEditProfilProgressBar.setVisibility(View.VISIBLE);
        //dialogButtonEdit.setVisibility(View.GONE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<EditProfilResponse> call = apiInterface.editProfilTanpaGambar(
                SharedPreference.getIdUser(getActivity()),
                SharedPreference.getJwtUser(getActivity()),
                dialogEditTextNama.getText().toString().trim(),
                SharedPreference.getNomorTelepon(getActivity()),
                SharedPreference.getFotoProfil(getActivity()),
                dialogEditTextAlamat.getText().toString().trim(),
                dialogEditTextEmail.getText().toString().trim()
                );

        setDisableAll();

        call.enqueue(new Callback<EditProfilResponse>() {

            @Override
            public void onResponse(Call<EditProfilResponse> call, retrofit2.Response<EditProfilResponse> response) {

                Log.d("coment", "onResponse");

                //dialogButtonEdit.setVisibility(View.VISIBLE);
                //dialogEditProfilProgressBar.setVisibility(View.GONE);
                setEnableAll();

                if (response.isSuccessful()) {
                    if (response.body().getStatus()){
                        Log.d("coment", "isSuccesful");
                        showToast(getString(R.string.submit_data_berhasil));
                        updateData();
                        dialogEditProfil.dismiss();
                    } else {
                        showToast(getString(R.string.akses_ditolak));
                    }
                } else {
                    Log.d("coment", "onUnResponse");
                    showToast(getString(R.string.terjadi_kesalahan));
                }
            }

            @Override
            public void onFailure(Call<EditProfilResponse> call, Throwable t) {
                setEnableAll();
                Log.d("coment", "onFailed");
                //dialogButtonEdit.setVisibility(View.VISIBLE);
                //dialogEditProfilProgressBar.setVisibility(View.GONE);
                showToast(getString(R.string.server_error));
            }
        });
    }

    private void getDataImage() {

        //dialogEditProfilProgressBar.setVisibility(View.VISIBLE);
        //dialogButtonEdit.setVisibility(View.GONE);

//        String filePath = getRealPathFromURIPath(imageUri, getActivity());
        String filePath = RealPathUtil.getRealPath(getActivity(), imageUri);
        final File file = new File(filePath);
        Log.d("File",""+file.getName());

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file); //membungkus file ke dalam request body
        MultipartBody.Part filegambar = MultipartBody.Part.createFormData("filegambar", file.getName(), mFile); // membuat formdata multipart berisi request body

        RequestBody requestBodyIdUser = RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getIdUser(getActivity()));
        RequestBody requestBodyJwtUser = RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getJwtUser(getActivity()));
        RequestBody requestBodyNama = RequestBody.create(MediaType.parse("text/plain"), dialogEditTextNama.getText().toString().trim());
        RequestBody requestBodyTelepon = RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getNomorTelepon(getActivity()));
        RequestBody requestBodyFotoProfil = RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getFotoProfil(getActivity()));
        RequestBody requestBodyAlamat = RequestBody.create(MediaType.parse("text/plain"), dialogEditTextAlamat.getText().toString().trim());
        RequestBody requestBodyEmail = RequestBody.create(MediaType.parse("text/plain"), dialogEditTextEmail.getText().toString().trim());

        Call<UpdateProfileResponse> call = apiInterface.editProfilDenganGambar(
                filegambar,
                requestBodyIdUser,
                requestBodyJwtUser,
                requestBodyNama,
                requestBodyTelepon,
                requestBodyFotoProfil,
                requestBodyAlamat,
                requestBodyEmail
        );

        setDisableAll();

        call.enqueue(new Callback<UpdateProfileResponse>() {

            @Override
            public void onResponse(Call<UpdateProfileResponse> call, retrofit2.Response<UpdateProfileResponse> response) {

                Log.d("coment", "onResponse");

                //dialogButtonEdit.setVisibility(View.VISIBLE);
                //dialogEditProfilProgressBar.setVisibility(View.GONE);
                setEnableAll();

                if (response.isSuccessful()) {
                    if (response.body().getStatus()){
                        Log.d("coment", "isSuccesful");
                        showToast(getString(R.string.submit_data_berhasil));
                        updateData();

                        SharedPreference.setFotoProfil(getActivity(), response.body().getPhoto());

                        Log.d("coment", SharedPreference.getFotoProfil(getActivity()));

                        imageViewProfil.setImageURI(imageUri);
                        zoomageView.setImageURI(imageUri);

                        dialogEditProfil.dismiss();
                    } else {
                        showToast(getString(R.string.akses_ditolak));
                    }
                } else {
                    Log.d("coment", "onUnResponse");
                    showToast(getString(R.string.terjadi_kesalahan));
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                setEnableAll();
                Log.d("coment", "onFailed");
                //dialogButtonEdit.setVisibility(View.VISIBLE);
                //dialogEditProfilProgressBar.setVisibility(View.GONE);
                showToast(getString(R.string.server_error));
            }
        });
    }

    private void getDataPassword() {

        //dialogPasswordProgressBar.setVisibility(View.VISIBLE);
        //dialogButtonKonfirmasi.setVisibility(View.GONE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<LogoutResponse> call = apiInterface.gantiPassword(
                SharedPreference.getIdUser(getActivity()),
                SharedPreference.getJwtUser(getActivity()),
                dialogEditTextPasswordLama.getText().toString().trim(),
                dialogEditTextPasswordBaru.getText().toString().trim(),
                dialogEditTextKonfirmasiPasswordBaru.getText().toString().trim()
        );

        setDisableAll();

        call.enqueue(new Callback<LogoutResponse>() {

            @Override
            public void onResponse(Call<LogoutResponse> call, retrofit2.Response<LogoutResponse> response) {

                Log.d("coment", "onResponse");

                //dialogButtonKonfirmasi.setVisibility(View.VISIBLE);
                //dialogPasswordProgressBar.setVisibility(View.GONE);
                setEnableAll();

                if (response.isSuccessful()) {
                    if (response.body().getStatus()){
                        if (!response.body().getError()){
                            Log.d("coment", "isSuccesful");
                            showToast(getString(R.string.submit_data_berhasil));
                            dialogEditPassword.dismiss();
                        } else {
                            showToast(getString(R.string.terjadi_kesalahan));
                        }
                    } else {
                        showToast(getString(R.string.akses_ditolak));
                    }
                } else {
                    Log.d("coment", "onUnResponse");
                    showToast(getString(R.string.terjadi_kesalahan));
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                setEnableAll();
                Log.d("coment", "onFailed");
                //dialogButtonKonfirmasi.setVisibility(View.VISIBLE);
                //dialogPasswordProgressBar.setVisibility(View.GONE);
                showToast(getString(R.string.server_error));
            }
        });
    }

    private void getDataLogout() {

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<LogoutResponse> call = apiInterface.logout(
                SharedPreference.getIdUser(getActivity()),
                SharedPreference.getJwtUser(getActivity()),
                DeviceIDUtil.getUniqueIMEIId(getActivity())
        );

        setDisableAllLogout();

        call.enqueue(new Callback<LogoutResponse>() {

            @Override
            public void onResponse(Call<LogoutResponse> call, retrofit2.Response<LogoutResponse> response) {

                Log.d("coment", "onResponse");

                setEnableAll();

                if (response.isSuccessful()) {
                    if (response.body().getStatus()){
                        if (!response.body().getError()){
                            Log.d("coment", "isSuccesful");
                            SharedPreference.setLogged(false);
                            startActivity(new Intent(getActivity(), InputTeleponActivity.class));
                            getActivity().finishAffinity();
                        } else {
                            showToast(getString(R.string.terjadi_kesalahan));
                        }
                    } else {
                        showToast(getString(R.string.akses_ditolak));
                    }
                } else {
                    Log.d("coment", "onUnResponse");
                    showToast(getString(R.string.terjadi_kesalahan));
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                setEnableAll();
                Log.d("coment", "onFailed");
                showToast(getString(R.string.server_error));
            }
        });
    }

    private void updateData() {
        SharedPreference.setNamaUser(getActivity(), dialogEditTextNama.getText().toString().trim());
        SharedPreference.setEmail(getActivity(), dialogEditTextEmail.getText().toString().trim());
        SharedPreference.setAlamat(getActivity(), dialogEditTextAlamat.getText().toString().trim());
        editTextNama.setText(dialogEditTextNama.getText().toString().trim());
        editTextEmail.setText(dialogEditTextEmail.getText().toString().trim());
        editTextAlamat.setText(dialogEditTextAlamat.getText().toString().trim());
    }

    public void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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

    public void setDisableAllLogout(){
        progressDialog.setTitle(getString(R.string.keluar));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}