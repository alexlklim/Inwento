package com.alex.asset.core.domain;

import com.alex.asset.core.domain.fields.*;
import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "products",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"code", "inventory_number"})})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean active;

    @JsonIgnore @CreatedDate @Column(name = "created")
    Date created;

    @JsonIgnore @LastModifiedDate @Column(name = "updated")
    Date updated;

    String title, description;
    Double price;

    // qr, rfid, ean etc
    @Column(name = "inventory_number")
    String inventoryNumber;
    String code;


    @Column(name = "created_by")
    UUID createdBy;
    UUID liable;
    String receiver;

    @ManyToOne @JoinColumn(name = "asset_status_id")
    AssetStatus assetStatus;

    @ManyToOne @JoinColumn(name = "kst_id")
    KST kst;

    @ManyToOne @JoinColumn(name = "unit_id")
    Unit unit;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "type_id")
    Type type;

    @ManyToOne @JoinColumn(name = "subtype_id")
    Subtype subtype;


    @ManyToOne @JoinColumn(name = "producer_id")
    Producer producer;

    @ManyToOne @JoinColumn(name = "supplier_id")
    Supplier supplier;

    @ManyToOne @JoinColumn(name = "branch_id")
    Branch branch;

    @ManyToOne @JoinColumn(name = "mpk_id")
    MPK mpk;


    String document;

    @Column(name = "document_date")
    Date documentDate;

    @Column(name = "warranty_period")
    Date warrantyPeriod;

    @Column(name = "inspection_date")
    Date inspectionDate;

    @Column(name = "last_inventory")
    Date lastInventoryDate;

    // location
    Double longitude, latitude;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "company_id")
    Company company;


}
