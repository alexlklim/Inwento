package com.alex.inwento.http.auth;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenDTO {

    @SerializedName("email")
    private String email;

    @SerializedName("refresh_token")
    private String refreshToken;


    public RefreshTokenDTO() {
    }

    public RefreshTokenDTO(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
