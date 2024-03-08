package com.alex.asset.company.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyDto {
    String company, info;

    String country,city, address;
    String logo;
    String phone, nip, regon, zipCode;
    LocalDate lastInventoryDate;

    String ownerFirstName, ownerLastName, ownerEmail;
}
