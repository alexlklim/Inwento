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
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "branches")
@Schema(description = "Branch")
public class Branch extends BaseEntityActive {
    @Schema(description = "Branch", example = "IT Department")
    String branch;

    public Branch(String branch) {
        this.branch = branch;
    }
}
