package com.alex.asset.core.repo;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrueAndCompany(Company company);

    boolean existsByBarCodeAndCompany(String barCode, Company company);
    boolean existsByRfidCodeAndCompany(String rfidCode, Company company);

    Optional<Product> findByActiveTrueAndIdAndCompany(Long id, Company company);

    Optional<Product> findByActiveTrueAndIdAndCompanyAndBarCode(Long id, Company company, String barCode);


    Optional<Product> findByActiveFalseAndIdAndCompany(Long id, Company company);
}
