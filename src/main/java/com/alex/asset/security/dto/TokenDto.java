package com.alex.asset.security.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Schema(description = "Token DTO")
@Data @Builder(toBuilder = true) @NoArgsConstructor @AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenDto {

    @Schema(description = "Refresh token", example = "d133f77b-21c5-4ecf-8576-9b79e7d46be7")
    private UUID refreshToken;

    @Schema(description = "Email", example = "alex@gmail.com")
    private String email;
}

