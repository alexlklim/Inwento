package com.alex.asset.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Schema(description = "User DTO")
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;


    @Schema(description = "Email", example = "alex@gmail.com")
    String email;

    @Schema(description = "First name", example = "Alex")
    private String firstName;

    @Schema(description = "Last name", example = "Klim")
    private String lastName;

    @Schema(description = "Phone", example = "+48 877 202 134")
    private String phone;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    boolean isActive;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime lastActivity;

    @Schema(description = "Role", example = "ADMIN")
    private String role;
}