package com.telu.eraporttelu.response;

import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelLogin;

import java.util.ArrayList;

public class LoadCPass {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private boolean data;

    public LoadCPass(String status, String message, boolean data) {
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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
