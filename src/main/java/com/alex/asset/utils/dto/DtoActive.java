package com.alex.asset.utils.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;


@ToString
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DtoActive {
    private Long id;
    private boolean isActive;
}
