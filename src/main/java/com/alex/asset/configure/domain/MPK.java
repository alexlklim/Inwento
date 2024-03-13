package com.alex.asset.configure.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "mpks")
@Schema(description = "MPK")
public class MPK extends BaseEntityActive {

    @Schema(description = "MPK", example = "MPK1")
    String mpk;

    public MPK(String mpk) {
        this.mpk = mpk;
    }
}
