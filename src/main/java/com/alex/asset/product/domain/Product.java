package com.alex.asset.product.domain;

import com.alex.asset.configure.domain.*;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;


@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(name = "is_active")
    boolean isActive;

    String title, description;
    Double price;

    @Column(name = "bar_code")
    String barCode;

    @Column(name = "rfid_code")
    String rfidCode;
    @Column(name = "inventory_number")
    String inventoryNumber;

    @Column(name = "serial_number")
    String serialNumber;


    @ManyToOne
    @JoinColumn(name = "created_by_id")
    @JsonIgnore
    User createdBy;

    @ManyToOne
    @JoinColumn(name = "liable_id")
    @JsonBackReference
    User liable;

    @Transient
    @JsonProperty("user_liable_id")
    Long userLiableId;

    @Transient
    @JsonProperty("user_liable_name")
    String userLiableName;

    String receiver;

    @ManyToOne
    @JoinColumn(name = "kst_id")
    KST kst;
    @ManyToOne
    @JoinColumn(name = "asset_status_id")
    AssetStatus assetStatus;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    Unit unit;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    Branch branch;
    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @ManyToOne
    @JoinColumn(name = "mpk_id")
    MPK mpk;

    @ManyToOne
    @JoinColumn(name = "type_id")
    Type type;
    @ManyToOne
    @JoinColumn(name = "subtype_id")
    Subtype subtype;

    String producer, supplier;

    @Column(name = "is_scrapping")
    boolean isScrapping;
    @Column(name = "scrapping_date")
    LocalDate scrappingDate;
    @Column(name = "scrapping_reason")
    String scrappingReason;

    String document;
    @Column(name = "document_date")
    LocalDate documentDate;
    @Column(name = "warranty_period")
    LocalDate warrantyPeriod;
    @Column(name = "inspection_date")
    LocalDate inspectionDate;


    Double longitude, latitude;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Comment> comments;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<ProductHistory> productHistories;

    @Override
    public String toString() {
        return "Product{" +
                "isActive=" + isActive +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", barCode='" + barCode + '\'' +
                ", rfidCode='" + rfidCode + '\'' +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", liable=" + (liable != null ? liable.getEmail() : null) +
                ", receiver='" + receiver + '\'' +
                ", kst=" + (kst != null ? kst.getKst() : null) +
                ", assetStatus=" + (assetStatus != null ? assetStatus.getAssetStatus() : null) +
                ", unit=" + (unit != null ? unit.getUnit() : null) +
                ", branch=" + (branch != null ? branch.getBranch() : null) +
                ", location=" + (location != null ? location.getLocation() : null) +
                ", mpk=" + (mpk != null ? mpk.getMpk() : null) +
                ", type=" + (type != null ? type.getType() : null) +
                ", subtype=" + (subtype != null ? subtype.getSubtype() : null) +
                ", producer='" + producer + '\'' +
                ", supplier='" + supplier + '\'' +
                ", isScrapping=" + isScrapping +
                ", scrappingDate=" + scrappingDate +
                ", scrappingReason='" + scrappingReason + '\'' +
                ", document='" + document + '\'' +
                ", documentDate=" + documentDate +
                ", warrantyPeriod=" + warrantyPeriod +
                ", inspectionDate=" + inspectionDate +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }



}
