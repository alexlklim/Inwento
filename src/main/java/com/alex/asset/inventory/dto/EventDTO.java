package com.alex.asset.inventory.dto;

import com.alex.asset.inventory.domain.event.UnknownProduct;
import com.alex.asset.product.dto.ProductV2Dto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Event DTO GET")
public class EventDTO {

    @Schema(description = "Branch", example = "Main office")
    Long branchId;

    @Schema(description = "Info", example = "Info about event")
    String info;

}
