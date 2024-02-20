package com.alex.asset.core.repo;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrueAndCompany(Company company);


    Optional<Product> findByActiveTrueAndIdAndCompany(Long id, Company company);


}
