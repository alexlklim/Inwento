package com.alex.asset.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDto {
     Long id;
     boolean isActive;
    @JsonFormat(pattern = "yyyy-MM-dd")
     LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd")
     LocalDateTime updated;
     String title;
     String description;
     Double price;
     String barCode;
     String rfidCode;
     Long createdById;
     Long liableId;
     String receiver;
     Long kstId;
     Long assetStatusId;
     Long unitId;
     Long branchId;
     Long mpkId;
     Long typeId;
     Long subtypeId;
     String producer;
     String supplier;
     boolean isScrapping;
     LocalDate scrappingDate;
     String scrappingReason;
     String document;
     LocalDate documentDate;
     LocalDate warrantyPeriod;
     LocalDate inspectionDate;
     LocalDate lastInventoryDate;
     Double longitude;
     Double latitude;
}




//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder(toBuilder = true)
//@FieldDefaults(level = AccessLevel.)
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//public class ProductDto {
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    Long id;
//    String title, description;
//    Double price;
//    String barCode, rfidCode;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    LocalDate created;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    LocalDate updated;
//
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    String createdByName;
//
//    String liableName;
//    String receiver;
//    String unit, kst, assetStatus;
//    String typeName, subtypeName, producer, supplier, branchName, mpkName;
//    String document;
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    LocalDate documentDate, warrantyPeriod, inspectionDate;
//
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    LocalDate lastInventoryDate;
//    Double longitude, latitude;
//
//}
