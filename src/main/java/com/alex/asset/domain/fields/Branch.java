package com.alex.asset.domain.fields;

import com.alex.asset.domain.BaseEntity;
import com.alex.asset.domain.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branches ")
public class Branch extends BaseEntity {

    private String branch;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "company_id")
    private Company company;

}
