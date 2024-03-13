package com.alex.asset.notification.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Schema(description = "Notification Dto")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationDto {

    @Schema(description = "Is read", example = "true")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    boolean isRead;

    @Schema(description = "Reason", example = "Inventarization")
    String reason;

    @Schema(description = "Message", example = "Inventarization is start, please, scan products in your office")
    String message;

    @Schema(description = "From who", example = "Inwento")
    String fromWho;

}
