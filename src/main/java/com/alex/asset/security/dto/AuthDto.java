package com.alex.asset.security.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthDto {
    UUID userId;
    String accessToken;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    Date expireAt;
    List<String> role = new ArrayList<>();
    // expiration time is 1 day 
    // sent request to refresh token if server return unauthorized ask to login again
    String refreshToken;
}

