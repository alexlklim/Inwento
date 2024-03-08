package com.alex.asset.company.dto;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.MPK;
import com.alex.asset.configure.domain.AssetStatus;
import com.alex.asset.configure.domain.Unit;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataDto {
    List<Employee> employees;

    List<Type> types;

    List<Unit> units;
    List<AssetStatus> assetStatuses;
    List<Branch> branches;
    List<MPK> MPKs;



    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Employee {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
    }


    @AllArgsConstructor
    @Data
    public static class Type {
        private Long id;
        private String type;
        private List<Subtype> subtypes;

    }

    @AllArgsConstructor
    @Data
    public static class Subtype {
        private Long id;
        private String subtype;
    }
}
