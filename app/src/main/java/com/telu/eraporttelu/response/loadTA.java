package com.telu.eraporttelu.response;

import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelDataGuru;
import com.telu.eraporttelu.model.modelDataTA;

import java.util.ArrayList;

public class loadTA {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<modelDataTA> data;

    public loadTA(String status, String message, ArrayList<modelDataTA> data) {
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

    public ArrayList<modelDataTA> getData() {
        return data;
    }

    public void setData(ArrayList<modelDataTA> data) {
        this.data = data;
    }
}
