package com.alex.asset.company.domain;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Label Dto")
public class LabelDto {


    @Schema(description = "Label width", example = "40.3")
    Long labelWidth;

    @Schema(description = "Label height", example = "20.0")
    Long labelHeight;

    @Schema(description = "Label type", example = "EAN")
    String labelType;
}
