package com.alex.asset.core.domain;

import com.alex.asset.security.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "company", uniqueConstraints = {@UniqueConstraint(columnNames = {"company"})})
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore @CreatedDate @Column(name = "created")
    LocalDateTime created;

    @JsonIgnore @LastModifiedDate @Column(name = "updated")
    LocalDateTime updated;

    String company, info;
    String country,city, address;

    String phone, nip, regon;
    String logo;

    @Column(name = "zip_code")
    String zipCode;

    @Column(name = "last_inventory_date")
    LocalDate lastInventoryDate;



    @ManyToOne @JoinColumn(name = "owner_id")
    User owner;

}
