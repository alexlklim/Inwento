package com.alex.asset.core.repo.product.constatns;

import com.alex.asset.core.domain.fields.constants.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetStatusRepo extends JpaRepository<AssetStatus, Long> {

    Optional<AssetStatus> findByAssetStatus(String assetStatus);
}
