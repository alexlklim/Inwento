package com.alex.asset.company.domain;

import com.alex.asset.utils.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "company", uniqueConstraints = {@UniqueConstraint(columnNames = {"company"})})
public class Company extends BaseEntity {

    String company;
    String city;
    String street;
    @Column(name = "zip_code")
    String zipCode;

    String nip;
    String regon;


    String phone;
    String email;

}
