package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BantuanResult {

    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("log")
    @Expose
    private String log;

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
