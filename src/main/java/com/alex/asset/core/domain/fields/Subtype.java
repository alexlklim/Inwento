package com.alex.asset.core.domain.fields;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "subtypes")
public class Subtype {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean active;

    private String subtype;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;

    public Subtype(String subtype, Type type) {
        this.active = true;
        this.subtype = subtype;
        this.type = type;
    }
}
