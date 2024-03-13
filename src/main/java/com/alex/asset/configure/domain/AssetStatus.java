package com.alex.asset.configure.domain;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Asset status")
public class AssetStatus extends BaseEntityActive {

    @Column(name = "asset_status")
    @Schema(description = "Asset status", example = "wycofane z użytkowania")
    String assetStatus;
}
