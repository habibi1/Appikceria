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

import com.bkipmlampung.appikceria.model.LoginResponse;
import com.bkipmlampung.appikceria.network.ApiService;
import com.bkipmlampung.appikceria.network.Client;
import com.bkipmlampung.appikceria.utils.DeviceIDUtil;
import com.bkipmlampung.appikceria.utils.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
import retrofit2.Response;

import static com.bkipmlampung.appikceria.utils.ControlUtil.elapseClick;

public class InputPasswordActivity extends AppCompatActivity {

    private int PHONE_STATE_CODE = 1;
    private int STATE_READ_EXTERNAL_STORAGE = 7;
    private int STATE_WRITE_EXTERNAL_STORAGE = 8;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    //private FirebaseAuth mAuth;

    TextInputEditText dialogEditTextKodeOTP;
    TextInputLayout dialogLayoutKodeOTP;
    MaterialButton dialogButtonNext;
    ImageView dialogImageViewClose;
    TextView dialogTextViewTimer;
    TextView dialogResendCode;
    Dialog DialogVerificationCode;
    TextView textViewTimer;
    TextView textViewNomorTelepon;
    TextView textViewLupaPassword;

    String TAG = "Auth";
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    private int time;

    TextInputEditText editTextPassword;
    TextInputLayout layoutPassword;
    MaterialButton buttonNext;
    ProgressBar progressBar, progressBarVerifikasi, progressBarResendCode;
    String NOMOR_TELEPON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_password);

        reqPhonePermission();

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("id");
        // [END initialize_auth]

        mAuth.signOut();

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

        textViewTimer = findViewById(R.id.waktu_verifikasi);

        editTextPassword = findViewById(R.id.et_password);
        layoutPassword = findViewById(R.id.layout_password);
        buttonNext = findViewById(R.id.btn_submit);
        progressBar = findViewById(R.id.progres_bar);
        textViewNomorTelepon = findViewById(R.id.tv_nomor_telepon);
        textViewLupaPassword = findViewById(R.id.tv_lupa_password);

        Intent intent = getIntent();
        NOMOR_TELEPON = intent.getStringExtra(getString(R.string.nomor_telepon));

        textViewNomorTelepon.setText(NOMOR_TELEPON);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVerificationInProgress){
                    ShowPopUp();
                } else if (checkField()){
                    disableAll();
                    getData();
                }
            }
        });

        textViewLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVerificationInProgress){
                    ShowPopUp();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    textViewLupaPassword.setVisibility(View.GONE);
                    startPhoneNumberVerification(NOMOR_TELEPON);
                }
            }
        });

        callBack();
    }

    private void reqPhonePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},PHONE_STATE_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STATE_READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STATE_WRITE_EXTERNAL_STORAGE);
        }
    }

    private boolean checkField(){
        boolean status = true;
        if (Objects.requireNonNull(editTextPassword.getText()).toString().trim().isEmpty()){
            layoutPassword.setError(getString(R.string.harus_diisi));
            status = false;
        } else if (editTextPassword.getText().toString().length() < 6) {
            layoutPassword.setError(getString(R.string.password_pendek));
            status = false;
        } else if (cekPassword(editTextPassword.getText().toString().toLowerCase())
        ) {
            layoutPassword.setError(getString(R.string.password_salah));
            status = false;
        } else {
            layoutPassword.setError(null);
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

        ApiService apiInterface = Client.getClient().create(ApiService.class);

        Call<LoginResponse> call = apiInterface.login(
                NOMOR_TELEPON,
                editTextPassword.getText().toString().trim(),
                DeviceIDUtil.getUniqueIMEIId(InputPasswordActivity.this)
        );

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                enableAll();

                Log.d("coment", "onResponse");

                if (response.isSuccessful()) {
                    Log.d("coment", "isSuccesful");
                    assert response.body() != null;
                    if (response.body().getStatus()){
                        LoginResponse loginResponse = response.body();
                        SharedPreference.setLogged(true);
                        SharedPreference.setIdUser(InputPasswordActivity.this, loginResponse.getId());
                        SharedPreference.setNamaUser(InputPasswordActivity.this, loginResponse.getUsername());
                        SharedPreference.setNomorTelepon(InputPasswordActivity.this, loginResponse.getNoHp());
                        SharedPreference.setAlamat(InputPasswordActivity.this, loginResponse.getAlamat());
                        SharedPreference.setEmail(InputPasswordActivity.this, loginResponse.getEmail());
                        SharedPreference.setJwtUser(InputPasswordActivity.this, loginResponse.getJwt());
                        SharedPreference.setFotoProfil(InputPasswordActivity.this, loginResponse.getPhoto());
                        updateUI();
                    }

                } else {
                    Log.d("coment", "onUnResponse");

                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.password_tidak_sesuai), Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .setActionTextColor(getResources().getColor(R.color.white))
                            .show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("coment", "onFailed");
                enableAll();

                Snackbar.make(findViewById(android.R.id.content), getString(R.string.server_error),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        startActivity(new Intent(InputPasswordActivity.this, BerandaActivity.class));
        finishAffinity();
    }

    public void disableAll(){
        editTextPassword.setEnabled(false);
        buttonNext.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void enableAll(){
        editTextPassword.setEnabled(true);
        buttonNext.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                progressBar.setVisibility(View.GONE);
                textViewLupaPassword.setVisibility(View.VISIBLE);
                updateUILupaPassword();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                mVerificationInProgress = false;
                progressBar.setVisibility(View.GONE);
                textViewLupaPassword.setVisibility(View.VISIBLE);
                enableAll();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    showToastOrSnackbar(getString(R.string.nomor_telepon_tidak_valid), true);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    showToastOrSnackbar(getString(R.string.terlalu_banyak_percobaan), true);
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId + " " + token);

                progressBar.setVisibility(View.GONE);
                textViewLupaPassword.setVisibility(View.VISIBLE);

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

                editTextPassword.setEnabled(true);
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
                    resendVerificationCode(NOMOR_TELEPON, mResendToken);
                } else {
                    Toast toast = Toast.makeText(InputPasswordActivity.this, getString(R.string.timer_belum_selesai), Toast.LENGTH_SHORT);
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

                            updateUILupaPassword();
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
            Toast toast = Toast.makeText(InputPasswordActivity.this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Snackbar.make(findViewById(android.R.id.content), message,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    public void updateUILupaPassword(){
        mAuth.signOut();
        Intent intent = new Intent(InputPasswordActivity.this, LupaPasswordActivity.class);
        intent.putExtra(getString(R.string.nomor_telepon), NOMOR_TELEPON);
        startActivity(intent);
        finish();
    }
}