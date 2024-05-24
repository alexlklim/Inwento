package com.alex.asset.utils.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@Data
@Schema(description = "Name Dto")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DtoName {

    @Schema(description = "Name", example = "Something")
    String name;

    public DtoName(String name) {
        this.name = name;
    }
}
