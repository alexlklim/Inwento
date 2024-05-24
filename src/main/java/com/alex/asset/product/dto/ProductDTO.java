package com.alex.asset.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Product DTO")
public class ProductDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Id", example = "101")
    Long id;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Active", example = "true")
    boolean active;

    @Schema(description = "Title", example = "Samsung Galaxy")
    String title;

    @Schema(description = "Description", example = "Good phone for all your needs")
    String description;

    @Schema(description = "Price", example = "2.500")
    Double price;

    @Schema(description = "Bar code", example = "1234567876543")
    String barCode;

    @Schema(description = "RFID code", example = "YTJDW54YH4554YGW")
    String rfidCode;

    @Schema(description = "Inventory number", example = "12234inv")
    String inventoryNumber;

    @Schema(description = "Serial number", example = "123serial")
    String serialNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Liable id", example = "10")
    Long liableId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Liable id", example = "10")
    String liableName;

    @Schema(description = "Receiver", example = "Alex Klim")
    String receiver;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "KST id", example = "10")
    Long kstId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Asset Status id", example = "10")
    Long assetStatusId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Unit id", example = "10")
    Long unitId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Branch id", example = "10")
    Long branchId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Location id", example = "10")
    Long locationId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "MPK id", example = "10")
    Long mpkId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Type id", example = "10")
    Long typeId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Subtype id", example = "10")
    Long subtypeId;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "KST id", example = "10")
    String kst;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Asset Status id", example = "10")
    String assetStatus;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Unit id", example = "10")
    String unit;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Branch id", example = "10")
    String branch;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Location id", example = "10")
    String location;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "MPK id", example = "10")
    String mpk;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Type id", example = "10")
    String type;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Subtype id", example = "10")
    String subtype;


    @Schema(description = "Producer", example = "Zebra")
    String producer;

    @Schema(description = "Supplier", example = "InPost")
    String supplier;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Is scrapping", example = "true")
    boolean scrapping;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Scrapping date", example = "2024-03-12")
    LocalDate scrappingDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Scrapping reason", example = "Uszkodzone")
    String scrappingReason;


    @Schema(description = "Document", example = "Document N1 Name of Document")
    String document;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Document date", example = "2024-03-12")
    LocalDate documentDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Warranty period", example = "2024-03-12")
    LocalDate warrantyPeriod;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Inspection date", example = "2024-03-12")
    LocalDate inspectionDate;

    @Schema(description = "Longitude", example = "-74.0060")
    Double longitude;

    @Schema(description = "Latitude", example = "-74.0060")
    Double latitude;


}

