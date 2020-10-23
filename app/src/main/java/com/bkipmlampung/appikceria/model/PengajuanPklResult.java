package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengajuanPklResult {

    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("tempat_lahir")
    @Expose
    private String tempatLahir;
    @SerializedName("tahun_lahir")
    @Expose
    private String tahunLahir;
    @SerializedName("nama_univ")
    @Expose
    private String namaUniv;
    @SerializedName("nama_jur")
    @Expose
    private String namaJur;
    @SerializedName("file_berkas")
    @Expose
    private String fileBerkas;
    @SerializedName("nilai_pkl")
    @Expose
    private String nilaiPkl;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("konfirmasi")
    @Expose
    private String konfirmasi;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("file_berkas_pkl")
    @Expose
    private String fileBerkasPkl;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTahunLahir() {
        return tahunLahir;
    }

    public void setTahunLahir(String tahunLahir) {
        this.tahunLahir = tahunLahir;
    }

    public String getNamaUniv() {
        return namaUniv;
    }

    public void setNamaUniv(String namaUniv) {
        this.namaUniv = namaUniv;
    }

    public String getNamaJur() {
        return namaJur;
    }

    public void setNamaJur(String namaJur) {
        this.namaJur = namaJur;
    }

    public String getFileBerkas() {
        return fileBerkas;
    }

    public void setFileBerkas(String fileBerkas) {
        this.fileBerkas = fileBerkas;
    }

    public String getNilaiPkl() {
        return nilaiPkl;
    }

    public void setNilaiPkl(String nilaiPkl) {
        this.nilaiPkl = nilaiPkl;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFileBerkasPkl() {
        return fileBerkasPkl;
    }

    public void setFileBerkasPkl(String fileBerkasPkl) {
        this.fileBerkasPkl = fileBerkasPkl;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}
