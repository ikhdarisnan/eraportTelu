package com.telu.eraporttelu.model;

public class modelDataTA {

    private String namaTA;
    private String statusTA;

    public modelDataTA(String namaTA, String statusTA) {
        this.namaTA = namaTA;
        this.statusTA = statusTA;
    }

    public String getNamaTA() {
        return namaTA;
    }

    public void setNamaTA(String namaTA) {
        this.namaTA = namaTA;
    }

    public String getStatusTA() {
        return statusTA;
    }

    public void setStatusTA(String statusTA) {
        this.statusTA = statusTA;
    }
}
