package com.alex.asset.utils.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;


@ToString
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Active Dto")
public class DtoActive {
    @Schema(description = "Id", example = "101")
    private Long id;
    @Schema(description = "Is active", example = "true")
    private boolean isActive;
}
