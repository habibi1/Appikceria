package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LaluLintasResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subjek_judul")
    @Expose
    private String subjekJudul;
    @SerializedName("file_foto")
    @Expose
    private String fileFoto;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("tahun")
    @Expose
    private String tahun;
    @SerializedName("bulan")
    @Expose
    private String bulan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjekJudul() {
        return subjekJudul;
    }

    public void setSubjekJudul(String subjekJudul) {
        this.subjekJudul = subjekJudul;
    }

    public String getFileFoto() {
        return fileFoto;
    }

    public void setFileFoto(String fileFoto) {
        this.fileFoto = fileFoto;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }
}
