package com.telu.eraporttelu.response;

import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelDataGuru;
import com.telu.eraporttelu.model.modelLogin;
import com.telu.eraporttelu.model.modelNilai;

import java.util.ArrayList;

public class loadLogin {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList<modelLogin> data;

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

    public ArrayList<modelLogin> getData() {
        return data;
    }

    public void setData(ArrayList<modelLogin> data) {
        this.data = data;
    }
}
