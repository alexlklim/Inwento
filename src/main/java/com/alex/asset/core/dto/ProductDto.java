package com.alex.asset.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDto {
    Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    boolean isActive;

    String title;
    String description;
    Double price;
    String barCode, rfidCode;
    Long liableId;
    String receiver;
    Long kstId, assetStatusId, unitId, branchId, mpkId, typeId, subtypeId;
    String producer, supplier;
    boolean isScrapping;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate scrappingDate;
    String scrappingReason;
    String document;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate documentDate, warrantyPeriod, inspectionDate, lastInventoryDate;
    Double longitude, latitude;
}

