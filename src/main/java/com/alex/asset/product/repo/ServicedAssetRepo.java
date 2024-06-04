package com.alex.asset.product.repo;

import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ServicedAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicedAssetRepo extends JpaRepository<ServicedAsset, Long> {

    List<ServicedAsset> findAllByProduct(Product product);
}
