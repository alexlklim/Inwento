package com.alex.asset.security.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDtoShort {
    String email;
    String firstname, lastname;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String companyName;
}