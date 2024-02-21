package com.alex.asset.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    String title, description;
    Double price;
    String barCode, rfidCode;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate updated;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID createdBy;

    UUID liableUUID;
    String receiver;
    String unit, kst, assetStatus;
    String typeName, subtypeName, producerName, supplierName, branchName, mpkName;
    String document;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate documentDate, warrantyPeriod, inspectionDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate lastInventoryDate;
    Double locLongitude, locLatitude;

}

