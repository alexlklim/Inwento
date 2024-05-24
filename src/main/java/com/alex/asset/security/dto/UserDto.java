package com.alex.asset.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "User DTO")
public class UserDto {
    @Schema(description = "User id", example = "10")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;


    @Schema(description = "Email", example = "alex@gmail.com")
    String email;

    @Schema(description = "First name", example = "Alex")
    String firstName;

    @Schema(description = "Last name", example = "Klim")
    String lastName;

    @Schema(description = "Phone", example = "+48 877 202 134")
    String phone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Is active", example = "true")
    boolean isActive;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Last activity", example = "2024-03-08 14:30")
    LocalDateTime lastActivity;

    @Schema(description = "Role", example = "ADMIN")
    String role;
}