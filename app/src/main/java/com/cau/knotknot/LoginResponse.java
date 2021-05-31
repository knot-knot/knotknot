package com.cau.knotknot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("family_code")
    @Expose
    private String familyCode;
    @SerializedName("error")
    @Expose
    private String error;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

