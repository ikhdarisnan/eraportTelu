package com.telu.eraporttelu.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelSiswa;

import java.util.ArrayList;

public class loadSiswa {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<modelSiswa> data;

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

    public ArrayList<modelSiswa> getData() {
        return data;
    }

    public void setData(ArrayList<modelSiswa> data) {
        this.data = data;
    }

}
