package com.telu.eraporttelu.model;

import com.google.gson.annotations.SerializedName;

public class modelLogin {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("status")
    private String statusLogin;

    public modelLogin(String username, String password, String statusLogin) {
        this.username = username;
        this.password = password;
        this.statusLogin = statusLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatusLogin() {
        return statusLogin;
    }

    public void setStatusLogin(String statusLogin) {
        this.statusLogin = statusLogin;
    }
}
