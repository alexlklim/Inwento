package com.alex.asset.core.repo.product;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.fields.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProducerRepo extends JpaRepository<Producer, Long> {
    Optional<Producer> findByProducerAndCompany(String producer, Company company);
    List<Producer> findByActiveTrueAndCompany(Company company);

}