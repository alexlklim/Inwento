package com.alex.asset.core.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataDto {
    List<Map<Integer, String>> employees;
    List<Map<Integer, String>> units;
    List<Map<Integer, String>> ksts;
    List<Map<Integer, String>> assetStatuses;

    Map<String, List<String>> types;

    List<Map<Integer, String>> branches;
    List<Map<Integer, String>> suppliers;
    List<Map<Integer, String>> producers;
    List<Map<Integer, String>> mpks;

}
