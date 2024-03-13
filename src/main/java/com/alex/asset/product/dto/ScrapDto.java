package com.alex.asset.product.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
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
@Schema(description = "Scrap Dto")
public class ScrapDto {

    @Schema(description = "Product id", example = "10")
    Long id; //product id
    @Schema(description = "Is scrap", example = "true")
    boolean isScrap;

    @Schema(description = "Scrapping reason", example = "Uszkodzone")
    String scrappingReason;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Scrapping date", example = "2024-03-12")
    LocalDate scrappingDate;


}
