package com.alex.asset.company.domain;


import com.alex.asset.configure.domain.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
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
    List<Location> locations;
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
