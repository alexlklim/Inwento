package com.alex.asset.core.repo;

import com.alex.asset.core.domain.Company;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    Optional<Company> findBySecretCode(UUID secretCode);

    @Query("SELECT c FROM Company c WHERE c.id = ?1")
    Company getCompany(Long id);

    Optional<Company> findByIdAndActiveTrueAndOwner(Long id, User user);

    Optional<Company> findByOwner(User user);

    Optional<Company> findByIdAndOwner(Long companyId, User user);
}
