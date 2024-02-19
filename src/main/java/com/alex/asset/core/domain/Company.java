package com.alex.asset.core.domain;

import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter @AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "companies")
public class Company {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID id;

    boolean active;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created")
    Date created;

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "updated")
    Date updated;

    String company, info;
    String country,city, address;


    @Column(name = "secret_code")
    UUID secretCode;

    @Column(name = "product_counter")
    Integer productCounter;

    @Column(name = "owner_id")
    UUID ownerId;


    @ManyToMany
    @JoinTable(
            name = "company_asset_status",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_status_id"))
    private List<AssetStatus> assetStatusEnums;

    @ManyToMany
    @JoinTable(
            name = "company_kst",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "kst_id"))
    private List<KST> ksts;

    @ManyToMany
    @JoinTable(
            name = "company_unit",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id"))
    private List<Unit> units;


}
