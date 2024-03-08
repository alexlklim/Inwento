package com.alex.asset.configure.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "types")
public class Type extends BaseEntityActive {

    String type;

    @JsonManagedReference
    @OneToMany(mappedBy = "type")
    private List<Subtype> subtypes = new ArrayList<>();


    public Type(String type) {
        this.type = type;
        this.subtypes = new ArrayList<>();
    }
}
