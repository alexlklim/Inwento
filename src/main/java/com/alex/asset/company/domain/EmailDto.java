package com.alex.asset.company.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor // Add this annotation
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Email Dto")
public class EmailDto {

    @Schema(description = "Is email configured", example = "true")
    boolean isEmailConfigured;

    @Schema(description = "Host", example = "smtp.gmail.com")
    String host;

    @Schema(description = "Port", example = "587")
    String port;

    @Schema(description = "Username", example = "asset.track.pro@gmail.com")
    String username;

    @Schema(description = "Password", example = "ubum fcfe xntw xcxq")
    String password;

    @Schema(description = "Protocol", example = "smtp")
    String protocol;





}
