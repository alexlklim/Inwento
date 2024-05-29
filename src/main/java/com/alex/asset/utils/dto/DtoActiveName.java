package com.alex.asset.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@ToString
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Active and Name Dto")
public class DtoActiveName {

    @JsonProperty("id")
    @Schema(description = "Id", example = "101")
    Long id;

    @JsonProperty("active")
    @Schema(description = "Is active", example = "true")
    boolean isActive;

    @JsonProperty("name")
    @Schema(description = "Name", example = "Something")
    String name;

}
