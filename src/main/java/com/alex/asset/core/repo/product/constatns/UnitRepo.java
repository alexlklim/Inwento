package com.alex.asset.core.repo.product.constatns;

import com.alex.asset.core.domain.fields.constants.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepo extends JpaRepository<Unit, Long> {


    Optional<Unit> findByUnit(String unit);
}
