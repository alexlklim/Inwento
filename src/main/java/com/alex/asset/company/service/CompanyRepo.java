package com.alex.asset.company.service;

import com.alex.asset.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    @Query("SELECT CASE WHEN " +
            "c.rfidCodePrefix IS NULL OR c.rfidCodePrefix = '' OR " +
            "c.rfidCodeSuffix IS NULL OR c.rfidCodeSuffix = '' OR " +
            "c.barCodePrefix IS NULL OR c.barCodePrefix = '' OR " +
            "c.barCodeSuffix IS NULL OR c.barCodeSuffix = '' " +
            "THEN true ELSE false END " +
            "FROM Company c " +
            "WHERE c.id = ?1")
    boolean areFieldsNullOrEmpty(Long companyId);

    @Query("SELECT CASE WHEN " +
            "(c.barCodeLength IS NULL OR c.barCodeLength = 0) " +
            "AND (c.rfidCodeLength IS NULL OR c.rfidCodeLength = 0) " +
            "THEN true ELSE false END FROM Company c WHERE c = ?1")
    boolean areNullOrZeroBarCodeLength(Company company);
}
