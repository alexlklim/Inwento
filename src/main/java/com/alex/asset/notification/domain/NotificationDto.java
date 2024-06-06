package com.alex.asset.notification.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;


@Schema(description = "Notification Dto")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationDto {

    @Schema(description = "Id", example = "1")
    Long id;

    @Schema(description = "Is read", example = "true")
    boolean isRead;

    @Schema(description = "Reason", example = "Inventarization")
    String reason;

    @Schema(description = "Message", example = "Inventarization is start, please, scan products in your office")
    String message;

    @Schema(description = "From who", example = "Inwento")
    String fromWho;


    @Schema(description = "Created", example = "2024-03-12")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime created;

    @Schema(description = "List of users id", example = "[1,2,3,4]")
    private List<Long> userIds;

}
