package com.alex.asset.core.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyDto {
    String company, info;
    String country,city, address;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID secretCode;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer productCounter;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID ownerUUID;
}
