package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PegawaiTeladanResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nip")
    @Expose
    private String nip;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("file_foto")
    @Expose
    private String fileFoto;
    @SerializedName("jabatan")
    @Expose
    private String jabatan;
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

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFileFoto() {
        return fileFoto;
    }

    public void setFileFoto(String fileFoto) {
        this.fileFoto = fileFoto;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
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
