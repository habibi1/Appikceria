package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveiModel {

    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("tanggal_survey")
    @Expose
    private String tanggalSurvey;
    @SerializedName("waktu_survey")
    @Expose
    private String waktuSurvey;
    @SerializedName("usia")
    @Expose
    private String usia;
    @SerializedName("jenis_layanan")
    @Expose
    private String jenisLayanan;
    @SerializedName("jenis_kelamin")
    @Expose
    private String jenisKelamin;
    @SerializedName("pendidikan")
    @Expose
    private String pendidikan;
    @SerializedName("pekerjaan")
    @Expose
    private String pekerjaan;
    @SerializedName("penilaian_1")
    @Expose
    private String penilaian1;
    @SerializedName("penilaian_2")
    @Expose
    private String penilaian2;
    @SerializedName("penilaian_3")
    @Expose
    private String penilaian3;
    @SerializedName("penilaian_4")
    @Expose
    private String penilaian4;
    @SerializedName("penilaian_5")
    @Expose
    private String penilaian5;
    @SerializedName("penilaian_6")
    @Expose
    private String penilaian6;
    @SerializedName("penilaian_7")
    @Expose
    private String penilaian7;
    @SerializedName("penilaian_8")
    @Expose
    private String penilaian8;
    @SerializedName("penilaian_9")
    @Expose
    private String penilaian9;
    @SerializedName("id_user")
    @Expose
    private String idUser;

    public SurveiModel(String nama, String tanggalSurvey, String waktuSurvey, String usia, String jenisLayanan, String jenisKelamin, String pendidikan, String pekerjaan, String penilaian1, String penilaian2, String penilaian3, String penilaian4, String penilaian5, String penilaian6, String penilaian7, String penilaian8, String penilaian9, String idUser) {
        this.nama = nama;
        this.tanggalSurvey = tanggalSurvey;
        this.waktuSurvey = waktuSurvey;
        this.usia = usia;
        this.jenisLayanan = jenisLayanan;
        this.jenisKelamin = jenisKelamin;
        this.pendidikan = pendidikan;
        this.pekerjaan = pekerjaan;
        this.penilaian1 = penilaian1;
        this.penilaian2 = penilaian2;
        this.penilaian3 = penilaian3;
        this.penilaian4 = penilaian4;
        this.penilaian5 = penilaian5;
        this.penilaian6 = penilaian6;
        this.penilaian7 = penilaian7;
        this.penilaian8 = penilaian8;
        this.penilaian9 = penilaian9;
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggalSurvey() {
        return tanggalSurvey;
    }

    public void setTanggalSurvey(String tanggalSurvey) {
        this.tanggalSurvey = tanggalSurvey;
    }

    public String getWaktuSurvey() {
        return waktuSurvey;
    }

    public void setWaktuSurvey(String waktuSurvey) {
        this.waktuSurvey = waktuSurvey;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getPenilaian1() {
        return penilaian1;
    }

    public void setPenilaian1(String penilaian1) {
        this.penilaian1 = penilaian1;
    }

    public String getPenilaian2() {
        return penilaian2;
    }

    public void setPenilaian2(String penilaian2) {
        this.penilaian2 = penilaian2;
    }

    public String getPenilaian3() {
        return penilaian3;
    }

    public void setPenilaian3(String penilaian3) {
        this.penilaian3 = penilaian3;
    }

    public String getPenilaian4() {
        return penilaian4;
    }

    public void setPenilaian4(String penilaian4) {
        this.penilaian4 = penilaian4;
    }

    public String getPenilaian5() {
        return penilaian5;
    }

    public void setPenilaian5(String penilaian5) {
        this.penilaian5 = penilaian5;
    }

    public String getPenilaian6() {
        return penilaian6;
    }

    public void setPenilaian6(String penilaian6) {
        this.penilaian6 = penilaian6;
    }

    public String getPenilaian7() {
        return penilaian7;
    }

    public void setPenilaian7(String penilaian7) {
        this.penilaian7 = penilaian7;
    }

    public String getPenilaian8() {
        return penilaian8;
    }

    public void setPenilaian8(String penilaian8) {
        this.penilaian8 = penilaian8;
    }

    public String getPenilaian9() {
        return penilaian9;
    }

    public void setPenilaian9(String penilaian9) {
        this.penilaian9 = penilaian9;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayanan) {
        this.jenisLayanan = jenisLayanan;
    }

    public String getUsia() {
        return usia;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }
}
