package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetodePengujianResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama_metode")
    @Expose
    private String namaMetode;
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

    public String getNamaMetode() {
        return namaMetode;
    }

    public void setNamaMetode(String namaMetode) {
        this.namaMetode = namaMetode;
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
