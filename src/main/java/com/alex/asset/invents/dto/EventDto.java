package com.alex.asset.invents.dto;


import com.alex.asset.product.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Schema(description = "Event DTO")
public class EventDto {
    @Schema(description = "Id", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Schema(description = "Is active", example = "true")
    boolean isActive;

    @Schema(description = "Invent id", example = "1")
    Long inventId;

    @Schema(description = "User id", example = "1")
    Long userId;

    @Schema(description = "Branch id", example = "1")
    Long branchId;

    @Schema(description = "Product shortage", example = "[...]")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<ProductDto> productsShortage;

    @Schema(description = "Product ok", example = "[...]")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<ProductDto> productsOk;

}
