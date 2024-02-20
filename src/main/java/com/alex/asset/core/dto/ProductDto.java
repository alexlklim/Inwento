package com.alex.asset.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String createdBy;

    UUID liableUUID;
    String receiver;
    String unit, kst, assetStatus;
    String typeName, subtypeName, producerName, supplierName, branchName, mpkName;
    String document;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime documentDate, warrantyPeriod, inspectionDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime lastInventoryDate;
    Double locLongitude, locLatitude;
}
