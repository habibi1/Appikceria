package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StrukturOrganisasiResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nip")
    @Expose
    private String nip;
    @SerializedName("nama_jabatan")
    @Expose
    private String namaJabatan;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("file_foto")
    @Expose
    private String fileFoto;
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

    public String getNamaJabatan() {
        return namaJabatan;
    }

    public void setNamaJabatan(String namaJabatan) {
        this.namaJabatan = namaJabatan;
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

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
