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
@Schema(description = "Product codes DTO")
public class ProductCodesDTO {

    @Schema(description = "Bar code", example = "203")
    Long id;

    @Schema(description = "Bar code", example = "3334445244")
    String barCode;

    @Schema(description = "Bar code", example = "ABC52C0697A6")
    String rfidCode;
}
