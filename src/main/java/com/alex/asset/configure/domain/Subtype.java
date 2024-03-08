package com.alex.asset.configure.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Table(name = "subtypes")
public class Subtype extends BaseEntityActive {


    String subtype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;

    public Subtype(String subtype, Type type) {
        this.subtype = subtype;
        this.type = type;
    }
}
