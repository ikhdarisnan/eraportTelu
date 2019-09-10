package com.telu.eraporttelu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class modelDataMapel {

    @SerializedName("idMapel")
    @Expose
    private String idMapel;
    @SerializedName("namaMapel")
    @Expose
    private String namaMapel;
    @SerializedName("NIPGuru")
    @Expose
    private String nIPGuru;
    @SerializedName("kkmMapel")
    @Expose
    private String kkmMapel;
    @SerializedName("nilaiMapel")
    @Expose
    private String nilaiMapel;
    @SerializedName("noteMapel")
    @Expose
    private String noteMapel;
    @SerializedName("namaGuru")
    @Expose
    private String namaGuru;
    @SerializedName("namaPanggilan")
    @Expose
    private String namaPanggilan;
    @SerializedName("usernameGuru")
    @Expose
    private String usernameGuru;
    @SerializedName("passwordGuru")
    @Expose
    private String passwordGuru;
    @SerializedName("idKelas")
    @Expose
    private String idKelas;
    @SerializedName("alamatGuru")
    @Expose
    private String alamatGuru;
    @SerializedName("kontakGuru")
    @Expose
    private String kontakGuru;
    @SerializedName("tempatTanggalLahir")
    @Expose
    private String tempatTanggalLahir;
    @SerializedName("isLogin")
    @Expose
    private String isLogin;

    public modelDataMapel(String idMapel, String namaMapel, String nIPGuru, String kkmMapel, String nilaiMapel, String noteMapel, String namaGuru, String namaPanggilan, String usernameGuru, String passwordGuru, String idKelas, String alamatGuru, String kontakGuru, String tempatTanggalLahir, String isLogin) {
        this.idMapel = idMapel;
        this.namaMapel = namaMapel;
        this.nIPGuru = nIPGuru;
        this.kkmMapel = kkmMapel;
        this.nilaiMapel = nilaiMapel;
        this.noteMapel = noteMapel;
        this.namaGuru = namaGuru;
        this.namaPanggilan = namaPanggilan;
        this.usernameGuru = usernameGuru;
        this.passwordGuru = passwordGuru;
        this.idKelas = idKelas;
        this.alamatGuru = alamatGuru;
        this.kontakGuru = kontakGuru;
        this.tempatTanggalLahir = tempatTanggalLahir;
        this.isLogin = isLogin;
    }

    public String getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(String idMapel) {
        this.idMapel = idMapel;
    }

    public String getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }

    public String getNIPGuru() {
        return nIPGuru;
    }

    public void setNIPGuru(String nIPGuru) {
        this.nIPGuru = nIPGuru;
    }

    public String getKkmMapel() {
        return kkmMapel;
    }

    public void setKkmMapel(String kkmMapel) {
        this.kkmMapel = kkmMapel;
    }

    public String getNilaiMapel() {
        return nilaiMapel;
    }

    public void setNilaiMapel(String nilaiMapel) {
        this.nilaiMapel = nilaiMapel;
    }

    public String getNoteMapel() {
        return noteMapel;
    }

    public void setNoteMapel(String noteMapel) {
        this.noteMapel = noteMapel;
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
