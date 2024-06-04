package com.alex.asset.product.repo;

import com.alex.asset.product.domain.ServicedAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicedAssetRepo extends JpaRepository<ServicedAsset, Long> {
}
