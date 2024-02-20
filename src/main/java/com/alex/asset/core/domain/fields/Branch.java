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
@Table(name = "branches")
public class Branch {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean active;

    private String branch;


    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Branch(String branch, Company company) {
        this.active = true;
        this.branch = branch;
        this.company = company;
    }
}
