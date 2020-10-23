package com.bkipmlampung.appikceria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bkipmlampung.appikceria.model.LogoutResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPasswordActivity extends AppCompatActivity {

    String NOMOR_TELEPON;
    TextView textViewNomorTelepon;
    TextInputEditText editTextPasswordBaru, editTextKonfirmasiPasswordBaru;
    TextInputLayout layoutPasswordBaru, layoutKonfirmasiPasswordBaru;
    MaterialButton buttonNext;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        textViewNomorTelepon = findViewById(R.id.tv_nomor_telepon);
        editTextPasswordBaru = findViewById(R.id.et_password_baru);
        editTextKonfirmasiPasswordBaru = findViewById(R.id.et_konfirmasi_password_baru);

        layoutPasswordBaru = findViewById(R.id.layout_password_baru);
        layoutKonfirmasiPasswordBaru = findViewById(R.id.layout_konfirmasi_password_baru);

        buttonNext = findViewById(R.id.btn_submit);
        progressBar = findViewById(R.id.progres_bar);

        Intent intent = getIntent();
        NOMOR_TELEPON = intent.getStringExtra(getString(R.string.nomor_telepon));

        textViewNomorTelepon.setText(NOMOR_TELEPON);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFieldPassword()){
                    getData();
                }
            }
        });

    }

    public void disableAll(){
        buttonNext.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        editTextPasswordBaru.setEnabled(false);
        editTextKonfirmasiPasswordBaru.setEnabled(false);
    }

    public void enableAll(){
        buttonNext.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        editTextPasswordBaru.setEnabled(true);
        editTextKonfirmasiPasswordBaru.setEnabled(true);
    }

    private boolean checkFieldPassword() {

        boolean status = true;

        if (Objects.requireNonNull(editTextPasswordBaru.getText()).toString().trim().isEmpty()){
            layoutPasswordBaru.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (editTextPasswordBaru.getText().toString().length() < 6) {
            layoutPasswordBaru.setError(getString(R.string.password_pendek));
            status = false;
        } else if (cekPassword(editTextPasswordBaru.getText().toString().toLowerCase())) {
            layoutPasswordBaru.setError(getString(R.string.password_salah));
            status = false;
        } else {
            layoutPasswordBaru.setError(null);
        }

        if (editTextKonfirmasiPasswordBaru.getText().toString().trim().isEmpty()){
            layoutKonfirmasiPasswordBaru.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (!Objects.requireNonNull(editTextKonfirmasiPasswordBaru.getText()).toString().trim().equals(editTextPasswordBaru.getText().toString().trim())){
            layoutKonfirmasiPasswordBaru.setError(getString(R.string.password_tidak_sama));
            status = false;
        } else if (editTextKonfirmasiPasswordBaru.getText().toString().length() < 6) {
            layoutKonfirmasiPasswordBaru.setError(getString(R.string.password_pendek));
            status = false;
        } else if (cekPassword(editTextKonfirmasiPasswordBaru.getText().toString().toLowerCase())) {
            layoutKonfirmasiPasswordBaru.setError(getString(R.string.password_salah));
            status = false;
        } else {
            layoutKonfirmasiPasswordBaru.setError(null);
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

    private void getData() {

        disableAll();

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<LogoutResponse> call = apiInterface.lupaPassword(
                NOMOR_TELEPON,
                editTextPasswordBaru.getText().toString().trim(),
                editTextKonfirmasiPasswordBaru.getText().toString().trim()
        );

        call.enqueue(new Callback<LogoutResponse>() {

            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {

                enableAll();

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (response.body().getStatus()){
                        Toast.makeText(LupaPasswordActivity.this, getString(R.string.ganti_password_berhasil), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.terjadi_kesalahan), Snackbar.LENGTH_LONG)
                                .setBackgroundTint(getResources().getColor(R.color.red))
                                .setActionTextColor(getResources().getColor(R.color.white))
                                .show();
                    }

                } else {
                    Log.d("coment", "onUnResponse");
                    enableAll();

                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.terjadi_kesalahan), Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .setActionTextColor(getResources().getColor(R.color.white))
                            .show();

                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                buttonNext.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                Snackbar.make(findViewById(android.R.id.content), getString(R.string.server_error),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}