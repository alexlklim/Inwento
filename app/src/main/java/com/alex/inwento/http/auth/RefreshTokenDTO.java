package com.alex.inwento.http.auth;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshTokenDTO {

    @SerializedName("email")
    private String email;

    @SerializedName("refresh_token")
    private String refreshToken;

}
