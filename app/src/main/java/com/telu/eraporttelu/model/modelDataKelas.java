package com.telu.eraporttelu.model;

public class modelDataKelas {

    private String idKelas;
    private String namaKelas;
    private String NIPGuru;
    private String namaGuru;
    private String namaPanggilan;
    private String usernameGuru;
    private String passwordGuru;
    private String alamatGuru;
    private String kontakGuru;
    private String tempatTanggalLahir;
    private String isLogin;

    public modelDataKelas(String idKelas, String namaKelas, String NIPGuru, String namaGuru, String namaPanggilan, String usernameGuru, String passwordGuru, String alamatGuru, String kontakGuru, String tempatTanggalLahir, String isLogin) {
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.NIPGuru = NIPGuru;
        this.namaGuru = namaGuru;
        this.namaPanggilan = namaPanggilan;
        this.usernameGuru = usernameGuru;
        this.passwordGuru = passwordGuru;
        this.alamatGuru = alamatGuru;
        this.kontakGuru = kontakGuru;
        this.tempatTanggalLahir = tempatTanggalLahir;
        this.isLogin = isLogin;
    }

    public String getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(String idKelas) {
        this.idKelas = idKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
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

    public String getUsernameGuru() {
        return usernameGuru;
    }

    public void setUsernameGuru(String usernameGuru) {
        this.usernameGuru = usernameGuru;
    }

    public String getPasswordGuru() {
        return passwordGuru;
    }

    public void setPasswordGuru(String passwordGuru) {
        this.passwordGuru = passwordGuru;
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
