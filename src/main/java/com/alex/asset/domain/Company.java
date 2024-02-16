package com.alex.asset.domain;

import com.alex.asset.domain.fields.constants.AssetStatus;
import com.alex.asset.domain.fields.constants.KST;
import com.alex.asset.domain.fields.constants.Unit;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter @AllArgsConstructor @NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "companies")
public class Company extends BaseEntity{

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
