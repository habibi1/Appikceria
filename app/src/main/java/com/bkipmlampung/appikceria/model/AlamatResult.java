package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlamatResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("longtitude")
    @Expose
    private String longtitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("no_hp")
    @Expose
    private String noHp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

}
