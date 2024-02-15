package com.alex.asset.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    String title, description;
    Double price;

    String inventoryNumber, code;

    UUID liable, createdBy;
    String receiver;


    String unit, kst, assetStatus;

    String typeName, subtypeName, producerName, supplierName, branchName, mpkName;

    String document;
    Date documentDate, warrantyPeriod, inspectionDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Date lastInventoryDate;
    // location
    Double locLongitude, locLatitude;
}
