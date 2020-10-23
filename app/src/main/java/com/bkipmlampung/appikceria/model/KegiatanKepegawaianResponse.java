package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KegiatanKepegawaianResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<KegiatanKepegawaianResult> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<KegiatanKepegawaianResult> getData() {
        return data;
    }

    public void setData(List<KegiatanKepegawaianResult> data) {
        this.data = data;
    }
}
