package com.telu.eraporttelu.response;

import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelDataGuru;

import java.util.ArrayList;

public class loadGuru {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<modelDataGuru> data;

    public loadGuru(String status, String message, ArrayList<modelDataGuru> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<modelDataGuru> getData() {
        return data;
    }

    public void setData(ArrayList<modelDataGuru> data) {
        this.data = data;
    }
}
