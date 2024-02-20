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
@Table(name = "mpks")
public class MPK {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean active;


    private String mpk;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public MPK(String mpk, Company company) {
        this.active = true;
        this.mpk = mpk;
        this.company = company;
    }
}
