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
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "branches")
public class Branch extends BaseEntityActive {

    String branch;

    public Branch(String branch) {
        this.branch = branch;
    }
}
