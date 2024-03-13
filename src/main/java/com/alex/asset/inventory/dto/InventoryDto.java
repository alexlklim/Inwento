package com.alex.asset.inventory.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;



@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Inventory DTO")
public class InventoryDto {

    @Schema(description = "Id", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Schema(description = "Start date", example = "2024-03-12")
    LocalDate startDate;

    @Schema(description = "Finish date (will be sel automatically", example = "2024-03-12")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDate finishDate;

    @Schema(description = "Is finished", example = "false")
    boolean isFinished;

    @Schema(description = "Info", example = "Please, check all products in your branch")
    String info;

}
