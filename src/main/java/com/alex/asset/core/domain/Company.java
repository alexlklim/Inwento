package com.alex.asset.core.domain;

import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.alex.asset.security.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "company",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"company"})})
public class Company {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;




    @JsonIgnore
    @CreatedDate
    @Column(name = "created")
    LocalDateTime created;

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "updated")
    LocalDateTime updated;

    String company, info;
    String country,city, address;


    @Column(name = "secret_code")
    UUID secretCode;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;


    @ManyToMany
    @JoinTable(
            name = "company_asset_status",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_status_id"))
    private List<AssetStatus> assetStatus;



    @ManyToMany
    @JoinTable(
            name = "company_unit",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    private List<Unit> units;

    @ManyToMany
    @JoinTable(
            name = "company_kst",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "kst_id"))
    private List<KST> ksts;  
}
