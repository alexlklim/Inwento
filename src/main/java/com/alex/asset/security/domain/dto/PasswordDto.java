package com.alex.asset.security.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
}
