package com.bkipmlampung.appikceria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaluLintasResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<LaluLintasResult> data = null;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<LaluLintasResult> getData() {
        return data;
    }

    public void setData(List<LaluLintasResult> data) {
        this.data = data;
    }

}
