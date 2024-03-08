package com.alex.asset.company.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyDto {

    @Schema(description = "Company name", example = "Cyfrowe Sieci Multimedialne")
    String company;

    @Schema(description = "City", example = "Stalowa Wola")
    String city;

    @Schema(description = "Street", example = "Kwiatkowskiego 1")
    String street;

    @Schema(description = "Zip code", example = "48-234")
    String zipCode;

    @Schema(description = "Nip", example = "123-456-78-90")
    String nip;

    @Schema(description = "Regon", example = "123456789")
    String regon;

    @Schema(description = "Phone", example = "+48 877 202 134")
    String phone;

    @Schema(description = "Email", example = "alex@gmail.com")
    String email;

}
