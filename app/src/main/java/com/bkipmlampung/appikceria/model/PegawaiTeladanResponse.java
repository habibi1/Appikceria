package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PegawaiTeladanResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<PegawaiTeladanResult> data = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<PegawaiTeladanResult> getData() {
        return data;
    }

    public void setData(List<PegawaiTeladanResult> data) {
        this.data = data;
    }
    
}
