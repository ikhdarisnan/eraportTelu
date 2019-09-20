package com.telu.eraporttelu.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelNilai;

import java.util.ArrayList;

public class loadNilai {

    @SerializedName("status")
    private String Status;

    @SerializedName("message")
    private String Message;

    @SerializedName("data")
    private String Data;

    @SerializedName("bundledata")
    private ArrayList<modelNilai> BundleData;

    public loadNilai(String status, String message,@Nullable String data, ArrayList<modelNilai> bundleData) {
        Status = status;
        Message = message;
        Data = data;
        BundleData = bundleData;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public ArrayList<modelNilai> getBundleData() {
        return BundleData;
    }

    public void setBundleData(ArrayList<modelNilai> bundleData) {
        BundleData = bundleData;
    }
}
