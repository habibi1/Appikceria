package com.bkipmlampung.appikceria;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bkipmlampung.appikceria.model.CekNomorTeleponResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.ui.bantuan.BantuanActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

import static com.bkipmlampung.appikceria.utils.ControlUtil.elapseClick;

public class InputTeleponActivity extends AppCompatActivity implements View.OnClickListener {

    private int PHONE_STATE_CODE = 1;
    private int CAMERA_STATE_CODE = 3;
    private int STATE_READ_EXTERNAL_STORAGE = 7;
    private int STATE_WRITE_EXTERNAL_STORAGE = 8;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    //private FirebaseAuth mAuth;

    TextInputEditText editTextTelepon;
    TextInputLayout layoutTelepon;
    MaterialButton buttonNext;
    TextView textViewTimer;

    TextInputEditText dialogEditTextKodeOTP;
    TextInputLayout dialogLayoutKodeOTP;
    MaterialButton dialogButtonNext;
    ImageView dialogImageViewClose;
    TextView dialogTextViewTimer;
    Dialog DialogVerificationCode;

    ProgressBar progressBar, progressBarVerifikasi, progressBarResendCode;

    String TAG = "Auth";
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    private int time;

    TextView textViewSyaratDanKetentuan, textViewKebijakanPrivasi, dialogResendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_telepon);
        FirebaseApp.initializeApp(this);
        reqPhonePermission();

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("id");
        // [END initialize_auth]

        mAuth.signOut();

        editTextTelepon = findViewById(R.id.et_telepon);
        buttonNext = findViewById(R.id.btn_next);
        layoutTelepon = findViewById(R.id.layout_telepon);
        textViewTimer = findViewById(R.id.waktu_verifikasi);
        textViewSyaratDanKetentuan = findViewById(R.id.tv_syarat_dan_ketentuan);
        textViewKebijakanPrivasi = findViewById(R.id.tv_kebijakan_privasi);
        progressBar = findViewById(R.id.progres_bar);

        DialogVerificationCode = new Dialog(this);
        DialogVerificationCode.setContentView(R.layout.dialog_konfirmasi_nomor_telepon);

        dialogEditTextKodeOTP = DialogVerificationCode.findViewById(R.id.et_kode_otp);
        dialogLayoutKodeOTP = DialogVerificationCode.findViewById(R.id.layout_kode_otp);
        dialogButtonNext = DialogVerificationCode.findViewById(R.id.btn_next);
        dialogImageViewClose = DialogVerificationCode.findViewById(R.id.iv_dismiss);
        dialogTextViewTimer = DialogVerificationCode.findViewById(R.id.waktu_verifikasi);
        dialogResendCode = DialogVerificationCode.findViewById(R.id.tv_resend_code);
        progressBarVerifikasi = DialogVerificationCode.findViewById(R.id.progressBar_verification);
        progressBarResendCode = DialogVerificationCode.findViewById(R.id.progressBar_resend_code);

        buttonNext.setOnClickListener(this);
        textViewSyaratDanKetentuan.setOnClickListener(this);
        textViewKebijakanPrivasi.setOnClickListener(this);

        callBack();

    }

    private void reqPhonePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},PHONE_STATE_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_STATE_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STATE_READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STATE_WRITE_EXTERNAL_STORAGE);
        }
    }

    private boolean checkField() {

        boolean status = true;

        if (Objects.requireNonNull(editTextTelepon.getText()).toString().trim().isEmpty()){
            layoutTelepon.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (editTextTelepon.getText().toString().trim().substring(0, 1).equals("0")){
            layoutTelepon.setError(getString(R.string.nomor_telepon_salah));
            status = false;
        } else {
            layoutTelepon.setError(null);
        }

        return status;
    }

    private void startPhoneNumberVerification(String phoneNumber) {

        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        mVerificationInProgress = true;
    }
    // [END resend_verification]

    private void callBack(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);

                updateUIRegister();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                buttonNext.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                mVerificationInProgress = false;
                enableAll();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    showToastOrSnackbar(getString(R.string.nomor_telepon_tidak_valid), true);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    showToastOrSnackbar(getString(R.string.terlalu_banyak_percobaan), true);
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId + " " + token);

                buttonNext.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                dialogResendCode.setVisibility(View.VISIBLE);
                progressBarResendCode.setVisibility(View.GONE);

                showToastOrSnackbar(getString(R.string.kode_terkirim), true);
                ShowPopUp();

                timer();

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };
    }

    private void updateUIRegister(){
        Intent intent = new Intent(InputTeleponActivity.this, RegistrasiActivity.class);
        intent.putExtra(getString(R.string.nomor_telepon), getString(R.string.prefix_indonesia) + editTextTelepon.getText().toString().trim());
        startActivity(intent);
    }

    private void updateUILogin() {
        mAuth.signOut();
        Intent intent = new Intent(InputTeleponActivity.this, InputPasswordActivity.class);
        intent.putExtra(getString(R.string.nomor_telepon), getString(R.string.prefix_indonesia) + editTextTelepon.getText().toString().trim());
        startActivity(intent);
    }

    public void timer(){
        mVerificationInProgress = true;
        time = 60;
        new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText("Timer: " + checkDigit(time));
                dialogTextViewTimer.setText("Timer: " + checkDigit(time));
                time--;
            }

            public void onFinish() {

                editTextTelepon.setEnabled(true);
                enableAll();

                textViewTimer.setText("");
                textViewTimer.setVisibility(View.GONE);
                dialogTextViewTimer.setText("");
                mVerificationInProgress = false;
            }
        }.start();
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public void ShowPopUp(){

        dialogButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elapseClick();

                String code = Objects.requireNonNull(dialogEditTextKodeOTP.getText()).toString();
                if (TextUtils.isEmpty(code)) {
                    dialogLayoutKodeOTP.setError(getString(R.string.harus_diisi));
                    return;
                } else {
                    dialogLayoutKodeOTP.setError(null);
                }

                progressBarVerifikasi.setVisibility(View.VISIBLE);
                dialogButtonNext.setVisibility(View.GONE);
                verifyPhoneNumberWithCode(mVerificationId, code);
                //updateUIRegister();
            }
        });

        dialogResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elapseClick();

                if (!mVerificationInProgress) {
                    dialogResendCode.setVisibility(View.GONE);
                    progressBarResendCode.setVisibility(View.VISIBLE);
                    resendVerificationCode(getString(R.string.prefix_indonesia) + editTextTelepon.getText().toString().trim(), mResendToken);
                } else {
                    Toast toast = Toast.makeText(InputTeleponActivity.this, getString(R.string.timer_belum_selesai), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        dialogImageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogVerificationCode.dismiss();
            }
        });

        Objects.requireNonNull(DialogVerificationCode.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DialogVerificationCode.getWindow().setGravity(Gravity.BOTTOM);
        DialogVerificationCode.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        DialogVerificationCode.setCanceledOnTouchOutside(false);
        DialogVerificationCode.getWindow().setWindowAnimations(R.style.DialogAnimation_up_down);
        DialogVerificationCode.show();
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]

                            progressBarVerifikasi.setVisibility(View.GONE);
                            dialogButtonNext.setVisibility(View.VISIBLE);

                            user.delete();

                            updateUIRegister();
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                progressBarVerifikasi.setVisibility(View.GONE);
                                dialogButtonNext.setVisibility(View.VISIBLE);
                                showToastOrSnackbar(getString(R.string.kode_otp_salah), true);
                                //mBinding.fieldVerificationCode.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            progressBarVerifikasi.setVisibility(View.GONE);
                            dialogButtonNext.setVisibility(View.VISIBLE);
                            showToastOrSnackbar(getString(R.string.terjadi_kesalahan), true);
                            // [START_EXCLUDE silent]
                            // Update UI
                            //updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void showToastOrSnackbar(String message, boolean statusToast){
        if (statusToast){
            Toast toast = Toast.makeText(InputTeleponActivity.this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Snackbar.make(findViewById(android.R.id.content), message,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_syarat_dan_ketentuan:
                Intent intent = new Intent(InputTeleponActivity.this, BantuanActivity.class);
                intent.putExtra(getString(R.string.pilihan), getString(R.string.snk));
                startActivity(intent);
                break;
            case R.id.tv_kebijakan_privasi:
                Intent intent1 = new Intent(InputTeleponActivity.this, BantuanActivity.class);
                intent1.putExtra(getString(R.string.pilihan), getString(R.string.kp));
                startActivity(intent1);
                break;
            case R.id.btn_next:
                if (mVerificationInProgress){
                    ShowPopUp();
                } else if (checkField()){
                    disableAll();
                    getData();
                }
                break;
        }
    }

    private void getData() {

        buttonNext.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiInterface = Client.getClient().create(ApiService.class);


        Call<CekNomorTeleponResponse> call = apiInterface.cekNomorTelepon(
                getString(R.string.prefix_indonesia) + editTextTelepon.getText().toString().trim()
        );
        call.enqueue(new Callback<CekNomorTeleponResponse>() {

            @Override
            public void onResponse(Call<CekNomorTeleponResponse> call, retrofit2.Response<CekNomorTeleponResponse> response) {

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (!response.body().getStatus()){
                        startPhoneNumberVerification(getString(R.string.prefix_indonesia) + editTextTelepon.getText().toString().trim());
                    } else {
                        enableAll();
                        buttonNext.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        updateUILogin();
                    }

                } else {
                    Log.d("coment", "onUnResponse");
                    buttonNext.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    enableAll();

                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.terjadi_kesalahan),
                            Snackbar.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CekNomorTeleponResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                buttonNext.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                enableAll();

                Snackbar.make(findViewById(android.R.id.content), getString(R.string.server_error),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void disableAll(){
        editTextTelepon.setEnabled(false);
    }

    public void enableAll(){
        editTextTelepon.setEnabled(true);
    }
}