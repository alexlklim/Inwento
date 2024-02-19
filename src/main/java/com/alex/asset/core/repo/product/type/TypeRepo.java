package com.alex.asset.core.repo.product.type;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.fields.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TypeRepo extends JpaRepository<Type, Long> {
    Optional<Type> findByTypeAndCompany(String type, Company company);
    List<Type> findByActiveTrueAndCompany(Company company);
}
