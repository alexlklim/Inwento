package com.alex.asset.core.domain.fields;

import com.alex.asset.core.domain.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class MPK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "is_active") @JsonIgnore
    boolean isActive;
    String mpk;

    public MPK(String mpk) {
        this.isActive = true;
        this.mpk = mpk;
    }
}
