package com.alex.asset.repo;

import com.alex.asset.domain.Company;
import com.alex.asset.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
    Optional<Product> findByInventoryNumber(String inventoryNumber);

    List<Product> findByCompany(Company company);
    List<Product> findByLiable(UUID liable);


}
