package com.alex.asset.company.domain;

import com.alex.asset.utils.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "company", uniqueConstraints = {@UniqueConstraint(columnNames = {"company"})})
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


    @Column(name = "label_width")
    Long labelWidth;

    @Column(name = "label_height")
    Long labelHeight;

    @Column(name = "label_type")
    String labelType;

    @Column(name = "is_email_configured")
    Boolean isEmailConfigured;
    String host, port, username, password, protocol;


}
