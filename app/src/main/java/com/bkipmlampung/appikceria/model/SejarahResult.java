package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SejarahResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subjek")
    @Expose
    private String subjek;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("file_foto")
    @Expose
    private String fileFoto;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("log")
    @Expose
    private String log;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjek() {
        return subjek;
    }

    public void setSubjek(String subjek) {
        this.subjek = subjek;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getFileFoto() {
        return fileFoto;
    }

    public void setFileFoto(String fileFoto) {
        this.fileFoto = fileFoto;
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
