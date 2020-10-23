package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeluhanPenyakitResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("no_hp")
    @Expose
    private String no_hp;
    @SerializedName("alamat_lengkap")
    @Expose
    private String alamatLengkap;
    @SerializedName("jenis_komoditas")
    @Expose
    private String jenisKomoditas;
    @SerializedName("luas_total_la_bu")
    @Expose
    private String luasTotalLaBu;
    @SerializedName("luas_ter_penyakit")
    @Expose
    private String luasTerPenyakit;
    @SerializedName("gejala_k")
    @Expose
    private String gejalaK;
    @SerializedName("jum_kematian")
    @Expose
    private String jumKematian;
    @SerializedName("tanggal_terjangkit")
    @Expose
    private String tanggalTerjangkit;
    @SerializedName("lokasi_budidaya")
    @Expose
    private String lokasiBudidaya;
    @SerializedName("nama_file")
    @Expose
    private String namaFile;
    @SerializedName("konfirmasi")
    @Expose
    private String konfirmasi;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("file_berkas")
    @Expose
    private String fileBerkas;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getJenisKomoditas() {
        return jenisKomoditas;
    }

    public void setJenisKomoditas(String jenisKomoditas) {
        this.jenisKomoditas = jenisKomoditas;
    }

    public String getLuasTotalLaBu() {
        return luasTotalLaBu;
    }

    public void setLuasTotalLaBu(String luasTotalLaBu) {
        this.luasTotalLaBu = luasTotalLaBu;
    }

    public String getLuasTerPenyakit() {
        return luasTerPenyakit;
    }

    public void setLuasTerPenyakit(String luasTerPenyakit) {
        this.luasTerPenyakit = luasTerPenyakit;
    }

    public String getGejalaK() {
        return gejalaK;
    }

    public void setGejalaK(String gejalaK) {
        this.gejalaK = gejalaK;
    }

    public String getJumKematian() {
        return jumKematian;
    }

    public void setJumKematian(String jumKematian) {
        this.jumKematian = jumKematian;
    }

    public String getTanggalTerjangkit() {
        return tanggalTerjangkit;
    }

    public void setTanggalTerjangkit(String tanggalTerjangkit) {
        this.tanggalTerjangkit = tanggalTerjangkit;
    }

    public String getLokasiBudidaya() {
        return lokasiBudidaya;
    }

    public void setLokasiBudidaya(String lokasiBudidaya) {
        this.lokasiBudidaya = lokasiBudidaya;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getFileBerkas() {
        return fileBerkas;
    }

    public void setFileBerkas(String fileBerkas) {
        this.fileBerkas = fileBerkas;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}
