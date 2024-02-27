package com.alex.asset.core.dto.simple;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;


@ToString
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ActiveDto {
    private Long id;
    private boolean isActive;
}
