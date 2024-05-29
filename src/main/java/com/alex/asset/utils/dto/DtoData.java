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
public class DtoData {

    @Schema(description = "Id", example = "101")
    Long id;

    @Schema(description = "Is active", example = "true")
    boolean isActive;

    @Schema(description = "Name", example = "Something")
    String name;

    @Schema(description = "Parent Id", example = "1")
    Long parentId;


    public DtoData(Long id, boolean isActive) {
        this.id = id;
        this.isActive = isActive;
    }

    public DtoData(String name) {
        this.name = name;
    }

    public DtoData(Long id, boolean isActive, Long parentId) {
        this.id = id;
        this.isActive = isActive;
        this.parentId = parentId;
    }

    public DtoData(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
