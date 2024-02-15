package com.alex.asset.repo;

import com.alex.asset.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    Optional<Company> findBySecretCode(UUID secretCode);
    Optional<Company> findByOwnerId(UUID ownerId);


}
