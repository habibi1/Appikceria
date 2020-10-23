package com.bkipmlampung.appikceria;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bkipmlampung.appikceria.ui.intro.IntroActivity;
import com.bkipmlampung.appikceria.utils.SharedPreference;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        super.onCreate(savedInstanceState);

        SharedPreference sharedPreference = new SharedPreference(SplashScreenActivity.this);

        if (!sharedPreference.isFirstTimeLaunch()){
            startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
            finish();
        } else if (sharedPreference.isLogged()){
            startActivity(new Intent(SplashScreenActivity.this, BerandaActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashScreenActivity.this, InputTeleponActivity.class));
            finish();
        }
    }
}