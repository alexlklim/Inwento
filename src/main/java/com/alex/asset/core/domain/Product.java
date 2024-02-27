package com.alex.asset.core.domain;

import com.alex.asset.core.domain.fields.*;
import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.alex.asset.security.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "is_active")
    boolean isActive;

    @CreatedDate @Column(name = "created")
    LocalDateTime created;
    @LastModifiedDate @Column(name = "updated")
    LocalDateTime updated;

    String title, description;
    Double price;

    @Column(name = "bar_code")
    String barCode;

    @Column(name = "rfid_code")
    String rfidCode;

    @ManyToOne @JoinColumn(name = "created_by_id")
    User createdBy;
    @ManyToOne @JoinColumn(name = "liable_id")
    User liable;
    String receiver;

    @ManyToOne @JoinColumn(name = "kst_id")
    KST kst;
    @ManyToOne @JoinColumn(name = "asset_status_id")
    AssetStatus assetStatus;
    @ManyToOne @JoinColumn(name = "unit_id")
    Unit unit;

    @ManyToOne @JoinColumn(name = "branch_id")
    Branch branch;
    @ManyToOne @JoinColumn(name = "mpk_id")
    MPK mpk;

    @ManyToOne @JsonIgnore @JoinColumn(name = "type_id")
    Type type;
    @ManyToOne @JoinColumn(name = "subtype_id")
    Subtype subtype;

    String producer, supplier;

    @Column(name = "is_scrapping")
    boolean isScrapping;
    @Column(name = "iscrapping_date")
    LocalDate scrappingDate;
    @Column(name = "is_scrapping_reason")
    String scrappingReason;

    String document;
    @Column(name = "document_date")
    LocalDate documentDate;
    @Column(name = "warranty_period")
    LocalDate warrantyPeriod;
    @Column(name = "inspection_date")
    LocalDate inspectionDate;

    @Column(name = "last_inventory_date")
    LocalDate lastInventoryDate;

    // location
    Double longitude, latitude;
}
