package com.alex.asset.utils.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Schema(description = "Name Dto")
public class DtoName {

    @Schema(description = "Name", example = "Something")
    private String name;

    public DtoName(String name) {
        this.name = name;
    }
}