package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlamatResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<AlamatResult> data = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<AlamatResult> getData() {
        return data;
    }

    public void setData(List<AlamatResult> data) {
        this.data = data;
    }

}
