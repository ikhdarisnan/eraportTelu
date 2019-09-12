package com.telu.eraporttelu.model;

public class modelDataGuru {

    private String NIPGuru;
    private String namaGuru;
    private String namaPanggilan;
    private String idKelas;
    private String alamatGuru;
    private String kontakGuru;
    private String tempatTanggalLahir;
    private String isLogin;

    public modelDataGuru(String NIPGuru, String namaGuru, String namaPanggilan, String idKelas, String alamatGuru, String kontakGuru, String tempatTanggalLahir, String isLogin) {
        this.NIPGuru = NIPGuru;
        this.namaGuru = namaGuru;
        this.namaPanggilan = namaPanggilan;
        this.idKelas = idKelas;
        this.alamatGuru = alamatGuru;
        this.kontakGuru = kontakGuru;
        this.tempatTanggalLahir = tempatTanggalLahir;
        this.isLogin = isLogin;
    }

    public String getNIPGuru() {
        return NIPGuru;
    }

    public void setNIPGuru(String NIPGuru) {
        this.NIPGuru = NIPGuru;
    }

    public String getNamaGuru() {
        return namaGuru;
    }

    public void setNamaGuru(String namaGuru) {
        this.namaGuru = namaGuru;
    }

    public String getNamaPanggilan() {
        return namaPanggilan;
    }

    public void setNamaPanggilan(String namaPanggilan) {
        this.namaPanggilan = namaPanggilan;
    }

    public String getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(String idKelas) {
        this.idKelas = idKelas;
    }

    public String getAlamatGuru() {
        return alamatGuru;
    }

    public void setAlamatGuru(String alamatGuru) {
        this.alamatGuru = alamatGuru;
    }

    public String getKontakGuru() {
        return kontakGuru;
    }

    public void setKontakGuru(String kontakGuru) {
        this.kontakGuru = kontakGuru;
    }

    public String getTempatTanggalLahir() {
        return tempatTanggalLahir;
    }

    public void setTempatTanggalLahir(String tempatTanggalLahir) {
        this.tempatTanggalLahir = tempatTanggalLahir;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }
}
