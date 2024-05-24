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
    Double labelWidth;

    @Column(name = "label_height")
    Double labelHeight;

    @Column(name = "label_type")
    String labelType;

    @Column(name = "is_email_configured")
    Boolean isEmailConfigured;
    String host, port, username, password, protocol;




    @Column(name = "rfid_length")
    Integer rfidCodeLength;

    @Column(name = "rfid_length_max")
    Integer rfidCodeLengthMax;

    @Column(name = "rfid_length_min")
    Integer rfidCodeLengthMin;

    @Column(name = "rfid_prefix")
    String rfidCodePrefix;

    @Column(name = "rfid_suffix")
    String rfidCodeSuffix;

    @Column(name = "rfid_postfix")
    String rfidCodePostfix;

    @Column(name = "bar_code_length")
    Integer barCodeLength;

    @Column(name = "bar_code_length_max")
    Integer barCodeLengthMax;

    @Column(name = "bar_code_length_min")
    Integer barCodeLengthMin;

    @Column(name = "bar_code_prefix")
    String barCodePrefix;

    @Column(name = "bar_code_suffix")
    String barCodeSuffix;

    @Column(name = "bar_code_postfix")
    String barCodePostfix;
}
