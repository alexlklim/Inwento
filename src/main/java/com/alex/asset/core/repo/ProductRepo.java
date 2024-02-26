package com.alex.asset.core.repo;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrueAndCompany(Company company);


    Optional<Product> findByActiveTrueAndIdAndCompany(Long id, Company company);



    Optional<Product> findByActiveFalseAndIdAndCompany(Long id, Company company);


    boolean existsByBarCodeOrRfidCode(String barCode, String rfidCode);



    List<Product> findByTitleContainingIgnoreCase(String title);



    Optional<Product> findByCompanyAndId(Company company, Long productId);

    Optional<Product> findByCompanyAndIdAndActiveTrue(Company company, Long productId);

    Optional<Product> findByCompanyAndIdAndActiveFalse(Company company, Long productId);

    List<Product> findByCompanyAndTitleContainingIgnoreCase(Company company, String title);
}
