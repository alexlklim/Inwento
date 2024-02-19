package com.alex.asset.core.domain.fields;

import com.alex.asset.core.domain.Company;
import com.alex.asset.utils.BaseEntity;
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
@Table(name = "suppliers")
public class Supplier  extends BaseEntity {


    private String supplier;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
