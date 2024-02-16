package com.alex.asset.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FieldsDto {
    List<String> employees;
    List<String> units;
    List<String> ksts;
    List<String> assetStatuses;
    List<String> types;
    List<String> subtypes;
    List<String> suppliers;
    List<String> producers;
    List<String> mpks;
    List<String> branches;
}


