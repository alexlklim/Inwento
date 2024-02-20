package com.alex.asset.core.domain.fields;

import com.alex.asset.core.domain.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "producers")
public class Producer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean active;

    private String producer;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Producer(String producer, Company company) {
        this.active = true;
        this.producer = producer;
        this.company = company;
    }
}
