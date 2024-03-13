package com.alex.asset.utils.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Number Dto")
public class DtoLong {
    @Schema(description = "Id", example = "101")
    Long id;
}
