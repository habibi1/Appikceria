package com.bkipmlampung.appikceria.ui.home.survei_kepuasan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bkipmlampung.appikceria.R;
import com.bkipmlampung.appikceria.model.SetDataResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.SharedPreference;
import com.bkipmlampung.appikceria.utils.StringUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class SurveiKepuasanActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton button;
    //ProgressBar progressBar;
    EditText editTextUsia, editTextNama, editTextKritikDanSaran;
    AutoCompleteTextView dropDownJenisKelamin, dropDownPendidikan, dropDownPekerjaan,
            dropDownJenisLayanan, dropDownJamSurvei, dropDownSurvei1, dropDownSurvei2, dropDownSurvei3,
            dropDownSurvei4, dropDownSurvei5, dropDownSurvei6, dropDownSurvei7,
            dropDownSurvei8, dropDownSurvei9;
    TextInputLayout layoutUsia, layoutJenisKelamin, layoutPendidikan, layoutPekerjaan,
            layoutJenisLayanan, layoutSurvei1, layoutSurvei2, layoutSurvei3,
            layoutSurvei4, layoutSurvei5, layoutSurvei6, layoutSurvei7,
            layoutSurvei8, layoutSurvei9, layoutJamSurvei, layoutKritikDanSaran;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survei_kepuasan);

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

        progressDialog = new ProgressDialog(SurveiKepuasanActivity.this);

        String[] JENIS_KELAMIN = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jenis_kelamin_menu);
        String[] PENDIDIKAN = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.pendidikan_menu);
        String[] PEKERJAAN = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.pekerjaan_menu);
        String[] JENIS_LAYANAN = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jenis_layanan_menu);
        String[] JAWABAN1 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_1_menu);
        String[] JAWABAN2 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_2_menu);
        String[] JAWABAN3 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_3_menu);
        String[] JAWABAN4 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_4_menu);
        String[] JAWABAN5 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_5_menu);
        String[] JAWABAN6 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_6_menu);
        String[] JAWABAN7 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_7_menu);
        String[] JAWABAN8 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_8_menu);
        String[] JAWABAN9 = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jawaban_survei_9_menu);
        String[] JAM_SURVEI = StringUtils.getArrayFromMenu(SurveiKepuasanActivity.this, R.menu.jam_survei_menu);

        ArrayAdapter<String> adapterJenisKelamin =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JENIS_KELAMIN);

        ArrayAdapter<String> adapterPendidikan =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        PENDIDIKAN);

        ArrayAdapter<String> adapterPekerjaan =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        PEKERJAAN);

        ArrayAdapter<String> adapterJenisLayanan =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JENIS_LAYANAN);

        ArrayAdapter<String> adapterJawabanSurvei1 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN1);

        ArrayAdapter<String> adapterJawabanSurvei2 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN2);

        ArrayAdapter<String> adapterJawabanSurvei3 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN3);

        ArrayAdapter<String> adapterJawabanSurvei4 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN4);

        ArrayAdapter<String> adapterJawabanSurvei5 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN5);

        ArrayAdapter<String> adapterJawabanSurvei6 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN6);

        ArrayAdapter<String> adapterJawabanSurvei7 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN7);

        ArrayAdapter<String> adapterJawabanSurvei8 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN8);

        ArrayAdapter<String> adapterJawabanSurvei9 =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAWABAN9);

        ArrayAdapter<String> adapterJamSurvei =
                new ArrayAdapter<>(
                        SurveiKepuasanActivity.this,
                        R.layout.dropdown_menu_popup_item,
                        JAM_SURVEI);

        button = findViewById(R.id.btn_submit);
        editTextNama = findViewById(R.id.et_nama);
        editTextUsia = findViewById(R.id.et_usia);
        editTextKritikDanSaran = findViewById(R.id.et_kritik_dan_saran);
        dropDownJenisKelamin = findViewById(R.id.act_jenis_kelamin);
        dropDownPendidikan = findViewById(R.id.act_pendidikan);
        dropDownPekerjaan = findViewById(R.id.act_pekerjaan);
        dropDownJenisLayanan = findViewById(R.id.act_jenis_layanan);
        dropDownSurvei1 = findViewById(R.id.act_survei_1);
        dropDownSurvei2 = findViewById(R.id.act_survei_2);
        dropDownSurvei3 = findViewById(R.id.act_survei_3);
        dropDownSurvei4 = findViewById(R.id.act_survei_4);
        dropDownSurvei5 = findViewById(R.id.act_survei_5);
        dropDownSurvei6 = findViewById(R.id.act_survei_6);
        dropDownSurvei7 = findViewById(R.id.act_survei_7);
        dropDownSurvei8 = findViewById(R.id.act_survei_8);
        dropDownSurvei9 = findViewById(R.id.act_survei_9);
        dropDownJamSurvei = findViewById(R.id.act_jam_survei);
        //progressBar = findViewById(R.id.progres_bar);

        editTextNama.setText(SharedPreference.getNamaUser(SurveiKepuasanActivity.this));

        layoutUsia = findViewById(R.id.layout_usia);
        layoutJenisKelamin = findViewById(R.id.layout_jenis_kelamin);
        layoutPendidikan = findViewById(R.id.layout_pendidikan);
        layoutPekerjaan = findViewById(R.id.layout_pekerjaan);
        layoutJenisLayanan = findViewById(R.id.layout_jenis_layanan);
        layoutSurvei1 = findViewById(R.id.layout_survei_1);
        layoutSurvei2 = findViewById(R.id.layout_survei_2);
        layoutSurvei3 = findViewById(R.id.layout_survei_3);
        layoutSurvei4 = findViewById(R.id.layout_survei_4);
        layoutSurvei5 = findViewById(R.id.layout_survei_5);
        layoutSurvei6 = findViewById(R.id.layout_survei_6);
        layoutSurvei7 = findViewById(R.id.layout_survei_7);
        layoutSurvei8 = findViewById(R.id.layout_survei_8);
        layoutSurvei9 = findViewById(R.id.layout_survei_9);
        layoutJamSurvei = findViewById(R.id.layout_jam_survei);
        layoutKritikDanSaran = findViewById(R.id.layout_kritik_dan_saran);

        dropDownJenisKelamin.setText(getString(R.string.pilih));
        dropDownJenisKelamin.setAdapter(adapterJenisKelamin);

        dropDownPendidikan.setText(getString(R.string.pilih));
        dropDownPendidikan.setAdapter(adapterPendidikan);

        dropDownPekerjaan.setText(getString(R.string.pilih));
        dropDownPekerjaan.setAdapter(adapterPekerjaan);

        //dropDownJenisLayanan.setText(adapterJenisLayanan.getItem(0), false);
        dropDownJenisLayanan.setText(getString(R.string.pilih));
        dropDownJenisLayanan.setAdapter(adapterJenisLayanan);

        dropDownSurvei1.setText(getString(R.string.pilih));
        dropDownSurvei1.setAdapter(adapterJawabanSurvei1);

        dropDownSurvei2.setText(getString(R.string.pilih));
        dropDownSurvei2.setAdapter(adapterJawabanSurvei2);

        dropDownSurvei3.setText(getString(R.string.pilih));
        dropDownSurvei3.setAdapter(adapterJawabanSurvei3);

        dropDownSurvei4.setText(getString(R.string.pilih));
        dropDownSurvei4.setAdapter(adapterJawabanSurvei4);

        dropDownSurvei5.setText(getString(R.string.pilih));
        dropDownSurvei5.setAdapter(adapterJawabanSurvei5);

        dropDownSurvei6.setText(getString(R.string.pilih));
        dropDownSurvei6.setAdapter(adapterJawabanSurvei6);

        dropDownSurvei7.setText(getString(R.string.pilih));
        dropDownSurvei7.setAdapter(adapterJawabanSurvei7);

        dropDownSurvei8.setText(getString(R.string.pilih));
        dropDownSurvei8.setAdapter(adapterJawabanSurvei8);

        dropDownSurvei9.setText(getString(R.string.pilih));
        dropDownSurvei9.setAdapter(adapterJawabanSurvei9);

        dropDownJamSurvei.setText(getString(R.string.pilih));
        dropDownJamSurvei.setAdapter(adapterJamSurvei);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if (checkField())
                    getData();
                break;
        }
    }

    private boolean checkField() {

        boolean status = true;

        if (Objects.requireNonNull(editTextKritikDanSaran.getText()).toString().trim().isEmpty()){
            layoutKritikDanSaran.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutKritikDanSaran.setError(null);
        }

        if (Objects.requireNonNull(editTextUsia.getText()).toString().trim().isEmpty()){
            layoutUsia.setError(getString(R.string.harus_diisi));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutUsia.setError(null);
        }

        if (Objects.requireNonNull(dropDownJenisKelamin.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutJenisKelamin.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutJenisKelamin.setError(null);
        }

        if (Objects.requireNonNull(dropDownPendidikan.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutPendidikan.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutPendidikan.setError(null);
        }

        if (Objects.requireNonNull(dropDownPekerjaan.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutPekerjaan.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutPekerjaan.setError(null);
        }

        if (Objects.requireNonNull(dropDownJenisLayanan.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutJenisLayanan.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutJenisLayanan.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei1.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei1.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei1.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei2.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei2.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei2.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei3.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei3.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei3.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei4.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei4.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei4.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei5.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei5.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei5.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei6.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei6.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei6.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei7.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei7.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei7.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei8.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei8.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei8.setError(null);
        }

        if (Objects.requireNonNull(dropDownSurvei9.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutSurvei9.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutSurvei9.setError(null);
        }

        if (Objects.requireNonNull(dropDownJamSurvei.getText()).toString().trim().equals(getString(R.string.pilih))){
            layoutJamSurvei.setError(getString(R.string.harus_dipilih));
            showSnackBarFieldEmpty();
            status = false;
        } else {
            layoutJamSurvei.setError(null);
        }

        return status;
    }

    private void showSnackBarFieldEmpty(){
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.field_kosong), Snackbar.LENGTH_LONG)
                .setBackgroundTint(getResources().getColor(R.color.red))
                .setActionTextColor(getResources().getColor(R.color.white))
                .show();
    }

    private void getData() {

        //progressBar.setVisibility(View.VISIBLE);
        //button.setVisibility(View.GONE);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
        String date = dateFormat.format(new Date());

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        String stringId = SharedPreference.getIdUser(SurveiKepuasanActivity.this);
        String stringJwt = SharedPreference.getJwtUser(SurveiKepuasanActivity.this);
        String stringNama = SharedPreference.getNamaUser(SurveiKepuasanActivity.this);
        String stringUsia = editTextUsia.getText().toString();
        String stringJenisLayanan = dropDownJenisLayanan.getText().toString();
        String stringWaktuSurvei = dropDownJamSurvei.getText().toString().trim();
        String stringJenisKelamin = dropDownJenisKelamin.getText().toString().trim();
        String stringPendidikan = dropDownPendidikan.getText().toString().trim();
        String stringPekerjaan = dropDownPekerjaan.getText().toString().trim();
        String stringPenilaian1 = dropDownSurvei1.getText().toString().trim().substring(3);
        String stringPenilaian2 = dropDownSurvei2.getText().toString().trim().substring(3);
        String stringPenilaian3 = dropDownSurvei3.getText().toString().trim().substring(3);
        String stringPenilaian4 = dropDownSurvei4.getText().toString().trim().substring(3);
        String stringPenilaian5 = dropDownSurvei5.getText().toString().trim().substring(3);
        String stringPenilaian6 = dropDownSurvei6.getText().toString().trim().substring(3);
        String stringPenilaian7 = dropDownSurvei7.getText().toString().trim().substring(3);
        String stringPenilaian8 = dropDownSurvei8.getText().toString().trim().substring(3);
        String stringPenilaian9 = dropDownSurvei9.getText().toString().trim().substring(3);
        String stringKritikDanSaran = editTextKritikDanSaran.getText().toString().trim();
        String stringNilai1 = dropDownSurvei1.getText().toString().trim().substring(0, 1);
        String stringNilai2 = dropDownSurvei2.getText().toString().trim().substring(0, 1);
        String stringNilai3 = dropDownSurvei3.getText().toString().trim().substring(0, 1);
        String stringNilai4 = dropDownSurvei4.getText().toString().trim().substring(0, 1);
        String stringNilai5 = dropDownSurvei5.getText().toString().trim().substring(0, 1);
        String stringNilai6 = dropDownSurvei6.getText().toString().trim().substring(0, 1);
        String stringNilai7 = dropDownSurvei7.getText().toString().trim().substring(0, 1);
        String stringNilai8 = dropDownSurvei8.getText().toString().trim().substring(0, 1);
        String stringNilai9 = dropDownSurvei9.getText().toString().trim().substring(0, 1);

        Call<SetDataResponse> call = apiInterface.setDataSurvei(
                stringId,
                stringJwt,
                stringNama,
                stringUsia,
                stringJenisLayanan,
                stringWaktuSurvei,
                stringJenisKelamin,
                stringPendidikan,
                stringPekerjaan,
                stringPenilaian1,
                stringPenilaian2,
                stringPenilaian3,
                stringPenilaian4,
                stringPenilaian5,
                stringPenilaian6,
                stringPenilaian7,
                stringPenilaian8,
                stringPenilaian9,
                stringKritikDanSaran,
                stringNilai1,
                stringNilai2,
                stringNilai3,
                stringNilai4,
                stringNilai5,
                stringNilai6,
                stringNilai7,
                stringNilai8,
                stringNilai9
        );

        setDisableAll();

        call.enqueue(new Callback<SetDataResponse>() {

            @Override
            public void onResponse(Call<SetDataResponse> call, retrofit2.Response<SetDataResponse> response) {

                Log.d("coment", "onResponse");

                //button.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                setEnableAll();

                if (response.isSuccessful()) {
                    if (response.body().getStatus()){
                        Log.d("coment", "isSuccesful");
                        showToast(getString(R.string.submit_data_berhasil));
                        finish();
                    } else {
                        showToast(getString(R.string.akses_ditolak));
                    }
                } else {
                    Log.d("coment", "onUnResponse");
                    showToast(getString(R.string.terjadi_kesalahan));
                }
            }

            @Override
            public void onFailure(Call<SetDataResponse> call, Throwable t) {
                setEnableAll();
                Log.d("coment", "onFailed");
                //button.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                showToast(getString(R.string.server_error));
            }
        });
    }

    public void showToast(String message){
        Toast.makeText(SurveiKepuasanActivity.this, message, Toast.LENGTH_SHORT).show();
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