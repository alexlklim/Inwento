package com.alex.asset.product.dto;


import com.alex.asset.product.domain.Activity;
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
@Schema(description = "Product History Dto")
public class ProductHistoryDto {

    @Schema(description = "Created", example = "2024-03-12 12:33")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime created;

    @Schema(description = "Username", example = "Alex Klim")
    String username;

    @Schema(description = "Email", example = "alex@gmail.com")
    String email;

    @Schema(description = "Activity", example = "LIABLE")
    Activity activity;

}
