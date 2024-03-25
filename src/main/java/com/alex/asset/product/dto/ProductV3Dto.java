package com.alex.asset.product.dto;


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
@Schema(description = "Product Dto representation (for android app)")
public class ProductV3Dto {

    @Schema(description = "Id", example = "101")
    Long id;

    @Schema(description = "Title", example = "Samsung Galaxy")
    String title;

    @Schema(description = "Description", example = "Good phone for all your needs")
    String description;

    @Schema(description = "Price", example = "2.500")
    Double price;

    @Schema(description = "Bar code", example = "1234567876543")
    String barCode;

    @Schema(description = "liable person", example = "Alex Klim")
    String liable;

    @Schema(description = "receiver", example = "Alex Klim")
    String receiver;
}
