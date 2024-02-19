package com.alex.asset.core.domain.fields;

import com.alex.asset.utils.BaseEntity;
import com.alex.asset.core.domain.Company;
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
@Table(name = "branches")
public class Branch extends BaseEntity {

    private String branch;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
