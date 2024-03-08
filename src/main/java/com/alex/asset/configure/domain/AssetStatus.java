package com.alex.asset.configure.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "asset_statuses")
public class AssetStatus extends BaseEntityActive {

    @Column(name = "asset_status")
    String assetStatus;
}
