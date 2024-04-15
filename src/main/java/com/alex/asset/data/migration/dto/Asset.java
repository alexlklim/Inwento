package com.alex.asset.data.migration.dto;

import com.alex.asset.configure.domain.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Asset {

    Integer id;
    String title;
    String description;
    Double price;

    String barCode;
    String rfidCode;
    String inventoryNumber;
    String serialNumber;

    String liableEmail;
    String createdByEmail;
    String receiver;

    KST kst;
    AssetStatus assetStatus;
    Unit unit;
    Branch branch;
    Location location;
    MPK mpk;
    Type type;
    Subtype subtype;
    String producer, supplier;
    String document;
    LocalDate documentDate;
    LocalDate warrantyPeriod;
    LocalDate inspectionDate;

    @Override
    public String toString() {
        return "Asset{" +
                "     ID    " + id +
                "title='" + (title != null ? title : "null") + '\'' +
                ", description='" + (description != null ? description : "null") + '\'' +
                ", price=" + (price != null ? price : "null") +
                ", barCode='" + (barCode != null ? barCode : "null") + '\'' +
                ", rfidCode='" + (rfidCode != null ? rfidCode : "null") + '\'' +
                ", inventoryNumber='" + (inventoryNumber != null ? inventoryNumber : "null") + '\'' +
                ", serialNumber='" + (serialNumber != null ? serialNumber : "null") + '\'' +
                ", liable=" + (liableEmail != null ? liableEmail : "null") +
                ", createdBy=" + createdByEmail +
                ", receiver='" + (receiver != null ? receiver : "null") + '\'' +
                ", kst=" + (kst != null ? kst.getKst() : "null") +
                ", assetStatus=" + (assetStatus != null ? assetStatus.getAssetStatus() : "null") +
                ", unit=" + (unit != null ? unit.getUnit() : "null") +
                ", branch=" + (branch != null ? branch.getBranch() : "null") +
                ", location =" + (location != null ? location.getLocation() : "null") +
                ", mpk=" + (mpk != null ? mpk.getMpk() : "null") +
                ", type=" + (type != null ? type.getType() : "null") +
                ", subtype=" + (subtype != null ? subtype.getSubtype() : "null") +
                ", producer='" + (producer != null ? producer : "null") + '\'' +
                ", supplier='" + (supplier != null ? supplier : "null") + '\'' +
                ", document='" + (document != null ? document : "null") + '\'' +
                ", documentDate=" + (documentDate != null ? documentDate : "null") +
                ", warrantyPeriod=" + (warrantyPeriod != null ? warrantyPeriod : "null") +
                ", inspectionDate=" + (inspectionDate != null ? inspectionDate : "null") +
                '}';
    }

}
