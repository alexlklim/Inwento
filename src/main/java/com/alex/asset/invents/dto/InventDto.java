package com.alex.asset.invents.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;



@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Invent DTO")
public class InventDto {

    @Schema(description = "Id", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    @Schema(description = "Is active", example = "true")
    boolean isActive;

    @Schema(description = "Start date", example = "2024-03-12")
    LocalDate startDate;

    @Schema(description = "Finish date", example = "2024-03-12")
    LocalDate finishDate;

    @Schema(description = "Info", example = "Please, check all products in your branch")
    String info;

}
