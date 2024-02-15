package com.alex.asset.domain.fields;

import com.alex.asset.domain.BaseEntity;
import com.alex.asset.domain.Company;
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
@Table(name = "mpks")
public class MPK extends BaseEntity {


    private String mpk;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
