package com.alex.asset.security.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Schema(description = "Password DTO")
@Getter @Setter @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PasswordDto {
    @Schema(description = "Current password", example = "1122")
    private String currentPassword;

    @Schema(description = "New password", example = "1122")
    private String newPassword;
}
