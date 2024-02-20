package com.alex.asset.core.repo;

import com.alex.asset.core.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, UUID> {
    Optional<Company> findBySecretCode(UUID secretCode);
    Optional<Company> findByOwnerId(UUID ownerId);


}
