package com.alex.asset.product.dto;


import com.alex.asset.inventory.domain.event.InventoryStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Product Dto (for short view)")
public class ProductV2Dto {

    @Schema(description = "Id", example = "101")
    Long id;

    @Schema(description = "Title", example = "Samsung Galaxy")
    String title;


    @Schema(description = "Bar code", example = "1234567876543")
    String barCode;


    @Schema(description = "Inventory Status", example = "SCANNED")
    InventoryStatus inventoryStatus;

}