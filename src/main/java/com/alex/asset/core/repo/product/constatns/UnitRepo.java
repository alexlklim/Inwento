package com.alex.asset.core.repo.product.constatns;

import com.alex.asset.core.domain.fields.constants.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnitRepo extends JpaRepository<Unit, Long> {



    @Query("SELECT u FROM Unit u WHERE u.isActive = true")
    List<Unit> getActive();


    @Modifying
    @Query("UPDATE Unit u SET u.isActive = ?1 WHERE u.id = ?2")
    void update(boolean bool, Long id);
}
