package com.alex.asset.a_security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Data @Builder @AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID id;
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    String firstname, lastname;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String companyName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long companyId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    boolean isEnabled;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime createdAt, updatedAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<String> role = new ArrayList<>();
}
