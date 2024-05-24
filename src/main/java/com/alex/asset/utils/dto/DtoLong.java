package com.alex.asset.utils.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Number Dto")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DtoLong {
    @Schema(description = "Id", example = "101")
    Long id;
}
