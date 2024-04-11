package com.alex.asset.utils.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Name Dto")
public class DtoName {

    @Schema(description = "Name", example = "Samsung galaxy")
    private String name;

    public DtoName() {
    }

    public DtoName(String name) {
        this.name = name;
    }
}
