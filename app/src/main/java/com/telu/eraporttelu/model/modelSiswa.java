package com.telu.eraporttelu.model;

public class modelSiswa {
    private String nisn;
    private String nama;

    public modelSiswa(String nisn, String nama) {
        this.nisn = nisn;
        this.nama = nama;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
