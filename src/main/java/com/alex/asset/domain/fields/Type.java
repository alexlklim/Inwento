package com.alex.asset.domain.fields;

import com.alex.asset.domain.BaseEntity;
import com.alex.asset.domain.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "types")
public class Type extends BaseEntity {

    private String type;

    @JsonManagedReference
    @OneToMany(mappedBy = "type")
    private List<Subtype> subtypes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
