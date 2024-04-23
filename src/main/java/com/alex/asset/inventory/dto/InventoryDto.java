package com.alex.asset.inventory.dto;


import com.alex.asset.inventory.domain.event.Event;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Data
@Getter
@Setter
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

    @Schema(description = "Amount of unknown products", example = "3")
    int unknownProductAmount;

    @Schema(description = "Total amount of products (without unknown)", example = "2000")
    int totalProductAmount;

    @Schema(description = "Amount of scanned product (without unknown)", example = "1500")
    int scannedProductAmount;

    List<Map<String, Object>> events;

}
