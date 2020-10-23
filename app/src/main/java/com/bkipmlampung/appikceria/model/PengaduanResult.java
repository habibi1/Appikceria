package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengaduanResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("pekerjaan_i")
    @Expose
    private String pekerjaanI;
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("bidang_pelayanan")
    @Expose
    private String bidangPelayanan;
    @SerializedName("tujuan_pengaduan")
    @Expose
    private String tujuanPengaduan;
    @SerializedName("sumber_informasi")
    @Expose
    private String sumberInformasi;
    @SerializedName("isi_aduan")
    @Expose
    private String isiAduan;
    @SerializedName("file_lampiran")
    @Expose
    private String fileLampiran;
    @SerializedName("konfirmasi")
    @Expose
    private String konfirmasi;
    @SerializedName("file_berkas")
    @Expose
    private String fileBerkas;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("id_user")
    @Expose
    private String idUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPekerjaanI() {
        return pekerjaanI;
    }

    public void setPekerjaanI(String pekerjaanI) {
        this.pekerjaanI = pekerjaanI;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getBidangPelayanan() {
        return bidangPelayanan;
    }

    public void setBidangPelayanan(String bidangPelayanan) {
        this.bidangPelayanan = bidangPelayanan;
    }

    public String getTujuanPengaduan() {
        return tujuanPengaduan;
    }

    public void setTujuanPengaduan(String tujuanPengaduan) {
        this.tujuanPengaduan = tujuanPengaduan;
    }

    public String getSumberInformasi() {
        return sumberInformasi;
    }

    public void setSumberInformasi(String sumberInformasi) {
        this.sumberInformasi = sumberInformasi;
    }

    public String getIsiAduan() {
        return isiAduan;
    }

    public void setIsiAduan(String isiAduan) {
        this.isiAduan = isiAduan;
    }

    public String getFileLampiran() {
        return fileLampiran;
    }

    public void setFileLampiran(String fileLampiran) {
        this.fileLampiran = fileLampiran;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
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

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

}
