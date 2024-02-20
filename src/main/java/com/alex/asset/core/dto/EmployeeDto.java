package com.alex.asset.core.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmployeeDto {
    UUID id;
    String email;
    String name;

    public EmployeeDto(UUID id ,String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}



