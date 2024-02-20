package com.alex.asset.core.repo;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
    Optional<Product> findByInventoryNumber(String inventoryNumber);

    List<Product> findByActiveTrueAndCompany(Company company);
    List<Product> findByLiable(UUID liable);

    Optional<Product> findByActiveTrueAndIdAndCompany(Long id, Company company);


}
