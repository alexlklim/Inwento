package com.alex.asset.repo.product;

import com.alex.asset.domain.Company;
import com.alex.asset.domain.fields.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findBySupplierAndCompany(String supplier, Company company);
    List<Supplier> findByActiveTrueAndCompany(Company company);
}
