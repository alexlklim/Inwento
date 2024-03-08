package com.alex.asset.configure.domain;

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
public class MPK extends BaseEntityActive {

    String mpk;

    public MPK(String mpk) {
        this.mpk = mpk;
    }
}
