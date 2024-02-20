package com.alex.asset.core.domain.fields;

import com.alex.asset.core.domain.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "suppliers")
public class Supplier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean active;


    private String supplier;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Supplier(String supplier, Company company) {
        this.active = true;
        this.supplier = supplier;
        this.company = company;
    }
}
