package com.alex.asset.invents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Event DTO GET")
public class EventV2Get {

    @Schema(description = "Id", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Schema(description = "Invent id", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long inventId;

    @Schema(description = "Username", example = "Alex Klim")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String username;

    @Schema(description = "Username", example = "alex@gmail.com")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String email;

    @Schema(description = "Branch", example = "Main office")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String branch;
}
