package com.alex.asset.utils.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@ToString
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Active Dto")
public class DtoActive {
    @Schema(description = "Id", example = "101")
    Long id;
    @Schema(description = "Is active", example = "true")
    boolean isActive;
}
