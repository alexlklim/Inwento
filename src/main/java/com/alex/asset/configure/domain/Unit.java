package com.alex.asset.configure.domain;

import com.alex.asset.utils.BaseEntityActive;
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
public class Unit extends BaseEntityActive {

    @Column(name = "unit")
    String unit;
}
