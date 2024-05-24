package com.alex.asset.logs.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Log Dto")
public class LogDto {

    @Schema(description = "Id", example = "10")
    Long id;

    @Schema(description = "Created", example = "2024-03-12")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime created;

    @Schema(description = "Email", example = "admin@gmail.com")
    String userEmail;

    @Schema(description = "Action", example = "UPDATE")
    Action action;

    @Schema(description = "Section", example = "COMPANY")
    Section section;

    @Schema(description = "Text", example = "Change name of company")
    String text;


}
