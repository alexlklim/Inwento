package com.alex.asset.repo.product.constatns;

import com.alex.asset.domain.fields.constants.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetStatusRepo extends JpaRepository<AssetStatus, Long> {
}
