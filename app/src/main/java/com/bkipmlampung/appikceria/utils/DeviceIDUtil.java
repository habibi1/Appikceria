package com.bkipmlampung.appikceria.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;

public class DeviceIDUtil {
    @SuppressLint("HardwareIds")
    public static String getUniqueIMEIId(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei;

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imei = "Android Q or newer - " + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert telephonyManager != null;
                imei = telephonyManager.getImei();
            } else {
                assert telephonyManager != null;
                imei = telephonyManager.getDeviceId();
            }

            if (imei != null && !imei.isEmpty()) {
                return "Permission granted - " + imei;
            } else {
                return "Permission granted - not found imei - " + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

        return "Permission denied - " + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
