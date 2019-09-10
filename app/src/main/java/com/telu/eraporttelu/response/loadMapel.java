package com.telu.eraporttelu.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelDataMapel;

import java.util.ArrayList;

public class loadMapel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<modelDataMapel> data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<modelDataMapel> getData() {
        return data;
    }

    public void setData(ArrayList<modelDataMapel> data) {
        this.data = data;
    }
}
