package com.alex.asset.configure.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "units")
@Schema(description = "Unit")
public class Unit extends BaseEntityActive {

    @Column(name = "unit")
    @Schema(description = "Unit", example = "szt.")
    String unit;
}
