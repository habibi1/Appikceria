package com.bkipmlampung.appikceria.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreference {

    static SharedPreferences pref;
    static SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "appik_ceria";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_LOGGED = "IsLogged";
    private static final String ID_PENGGUNA = "IdPengguna";
    private static final String NAMA_USER = "NamaUser";
    private static final String NOMOR_TELEPON = "NomorTelepon";
    private static final String ALAMAT = "Alamat";
    private static final String EMAIL = "Email";
    private static final String FOTO_PROFIL = "FotoProfil";
    private static final String JWT = "JWT";

    @SuppressLint("CommitPrefEdits")
    public SharedPreference(Context context){
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /** Pendlakarasian Shared Preferences yang berdasarkan paramater context */
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, false);
    }

    public static void setLogged(boolean isLogged) {
        editor.putBoolean(IS_LOGGED, isLogged);
        editor.commit();
    }

    public boolean isLogged() {
        return pref.getBoolean(IS_LOGGED, false);
    }

    public static void setIdUser(Context context, String id_user) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(ID_PENGGUNA, id_user);
        editor.apply();
    }

    public static String getIdUser(Context context) {
        return getSharedPreference(context).getString(ID_PENGGUNA, "");
    }

    public static void setJwtUser(Context context, String username) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(JWT, username);
        editor.apply();
    }

    public static String getJwtUser(Context context) {
        return getSharedPreference(context).getString(JWT, "");
    }

    public static void setNamaUser(Context context, String namaUser) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(NAMA_USER, namaUser);
        editor.apply();
    }

    public static String getNamaUser(Context context) {
        return getSharedPreference(context).getString(NAMA_USER, "");
    }

    public static void setNomorTelepon(Context context, String nomorTelepon) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(NOMOR_TELEPON, nomorTelepon);
        editor.apply();
    }

    public static String getNomorTelepon(Context context) {
        return getSharedPreference(context).getString(NOMOR_TELEPON, "");
    }

    public static void setAlamat(Context context, String alamat) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(ALAMAT, alamat);
        editor.apply();
    }

    public static String getAlamat(Context context) {
        return getSharedPreference(context).getString(ALAMAT, "");
    }

    public static void setEmail(Context context, String email) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public static String getEmail(Context context) {
        return getSharedPreference(context).getString(EMAIL, "");
    }

    public static void setFotoProfil(Context context, String foto) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(FOTO_PROFIL, foto);
        editor.apply();
    }

    public static String getFotoProfil(Context context) {
        return getSharedPreference(context).getString(FOTO_PROFIL, "");
    }
}
