package com.alex.asset.core.dto;

import lombok.Data;

import java.util.List;


@Data
public class TypeDto {
    private Long id;
    private String type;
    private List<SubtypeClass> subtypes;

    static class SubtypeClass{
        private Long id;
        private String subtype;
    }

}
