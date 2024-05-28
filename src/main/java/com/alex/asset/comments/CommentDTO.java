package com.alex.asset.comments;

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
@Schema(description = "Comment DTO")
public class CommentDTO {
    @Schema(description = "Created", example = "2024-03-12 12:40")
    LocalDateTime created;

    @Schema(description = "Comment", example = "comment about product")
    String comment;

    @Schema(description = "user name", example = "Alex Klim")
    String userName;
}
