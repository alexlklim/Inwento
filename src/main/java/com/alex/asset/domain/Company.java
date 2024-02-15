package com.alex.asset.domain;

import com.alex.asset.domain.enums.AssetStatus;
import com.alex.asset.domain.enums.KST;
import com.alex.asset.domain.enums.Unit;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @ElementCollection
    Set<UUID> employees = new HashSet<>();




    @ElementCollection(targetClass = KST.class)
    @CollectionTable(name = "company_kst", joinColumns = @JoinColumn(name = "company_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "ksts")
    Set<KST> ksts = new HashSet<>();

    @ElementCollection(targetClass = AssetStatus.class)
    @CollectionTable(name = "company_asset_status", joinColumns = @JoinColumn(name = "company_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "statuses")
    Set<AssetStatus> statuses = new HashSet<>();

    @ElementCollection(targetClass = Unit.class)
    @CollectionTable(name = "company_unit", joinColumns = @JoinColumn(name = "company_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "units")
    Set<Unit> units = new HashSet<>();



    public void addEmployee(UUID uuid) {
        employees.add(uuid);
    }

    public void removeEmployee(UUID uuid) {
        employees.remove(uuid);
    }



    public void deleteStatus(AssetStatus status) {
        statuses.remove(status);
    }

    public void deleteUnit(Unit unit) {
        units.remove(unit);
    }

    public void deleteKst(KST kst) {
        ksts.remove(kst);
    }





}
