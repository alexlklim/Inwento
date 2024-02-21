package com.alex.asset.core.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FieldsDto {
    List<String> employees;
    List<String> units;
    List<String> ksts;
    List<String> assetStatuses;

    Map<String, List<String>> types;

    List<String> branches;
    List<String> suppliers;
    List<String> producers;
    List<String> mpks;

}


