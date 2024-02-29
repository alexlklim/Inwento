package com.alex.asset.security.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    String email;

    String firstName, lastName, phone;

    boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime lastActivity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime createdAt, updatedAt;

    List<String> role = new ArrayList<>();
}