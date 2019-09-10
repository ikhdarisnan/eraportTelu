package com.telu.eraporttelu.response;

import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelDataKelas;

import java.util.ArrayList;

public class loadKelas {

    @SerializedName("status")
    private String Status;

    @SerializedName("message")
    private String Message;

    @SerializedName("data")
    private ArrayList<modelDataKelas> Data;

    public loadKelas(String status, String message, ArrayList<modelDataKelas> data) {
        Status = status;
        Message = message;
        Data = data;
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

    public ArrayList<modelDataKelas> getData() {
        return Data;
    }

    public void setData(ArrayList<modelDataKelas> data) {
        Data = data;
    }
}
