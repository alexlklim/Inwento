package com.alex.asset.repo.product;

import com.alex.asset.domain.Company;
import com.alex.asset.domain.fields.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepo extends JpaRepository<Branch, Long> {
    Optional<Branch> findByBranchAndCompany(String branch, Company company);
    List<Branch> findByActiveTrueAndCompany(Company company);
}
