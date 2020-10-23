package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenyakitIkanResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama_penyakit")
    @Expose
    private String namaPenyakit;
    @SerializedName("nama_latin")
    @Expose
    private String namaLatin;
    @SerializedName("gejala")
    @Expose
    private String gejala;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("file_berkas")
    @Expose
    private String fileBerkas;
    @SerializedName("log")
    @Expose
    private String log;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaPenyakit() {
        return namaPenyakit;
    }

    public void setNamaPenyakit(String namaPenyakit) {
        this.namaPenyakit = namaPenyakit;
    }

    public String getNamaLatin() {
        return namaLatin;
    }

    public void setNamaLatin(String namaLatin) {
        this.namaLatin = namaLatin;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFileBerkas() {
        return fileBerkas;
    }

    public void setFileBerkas(String fileBerkas) {
        this.fileBerkas = fileBerkas;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
