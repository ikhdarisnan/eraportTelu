package com.telu.eraporttelu.response;

import com.google.gson.annotations.SerializedName;
import com.telu.eraporttelu.model.modelDataKelas;
import com.telu.eraporttelu.model.modelNilai;

import java.util.ArrayList;

public class loadNilai {

    @SerializedName("status")
    private String Status;

    @SerializedName("message")
    private String Message;

    @SerializedName("data")
    private ArrayList<modelNilai> Data;

    public loadNilai(String status, String message, ArrayList<modelNilai> data) {
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

    public ArrayList<modelNilai> getData() {
        return Data;
    }

    public void setData(ArrayList<modelNilai> data) {
        Data = data;
    }
}
