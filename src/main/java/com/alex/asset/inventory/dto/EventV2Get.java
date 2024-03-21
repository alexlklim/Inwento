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

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Event DTO GET")
public class EventV2Get {

    @Schema(description = "Id", example = "1")
    Long id;


    @Schema(description = "Username", example = "Alex Klim")
    String username;

    @Schema(description = "Email", example = "alex@gmail.com")
    String email;

    @Schema(description = "Branch", example = "Main office")
    String branch;


    @Schema(description = "Unknown Products", example = "[...]")
    List<UnknownProduct> unknownProducts;

    @Schema(description = "Products", example = "[...]")
    List<ProductV2Dto> products;


    int unknownProductAmount;
    int totalProductAmount;
    int scannedProductAmount;

}
