package com.alex.asset.company.domain;

import com.alex.asset.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
