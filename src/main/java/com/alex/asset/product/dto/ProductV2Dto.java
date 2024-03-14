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
@Schema(description = "Product Dto representation for inventory")
public class ProductV2Dto {
    Long id;
    String title;
    String barCode;
    InventoryStatus inventoryStatus;
}
