package com.alex.asset.company.repo;

import com.alex.asset.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepo extends JpaRepository<Company, Long> {


    @Query("SELECT c FROM Company c WHERE c.id = ?1")
    Company getCompany(Long id);


}
