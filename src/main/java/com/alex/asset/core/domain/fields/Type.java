package com.alex.asset.core.domain.fields;

import com.alex.asset.core.domain.Company;
import com.alex.asset.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "types")
public class Type extends BaseEntity {

    private String type;

    @JsonManagedReference
    @OneToMany(mappedBy = "type")
    private List<Subtype> subtypes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    public Type(String type) {
        super(true);
        this.type = type;
    }
}
