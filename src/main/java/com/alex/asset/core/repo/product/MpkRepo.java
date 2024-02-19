package com.alex.asset.core.repo.product;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.fields.MPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MpkRepo extends JpaRepository<MPK, Long> {

    Optional<MPK> findByMpkAndCompany(String mpk ,Company company);
    List<MPK> findByActiveTrueAndCompany(Company company);
}