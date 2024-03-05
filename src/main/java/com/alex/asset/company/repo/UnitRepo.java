package com.alex.asset.company.repo;

import com.alex.asset.company.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnitRepo extends JpaRepository<Unit, Long> {



    @Query("SELECT u FROM Unit u WHERE u.isActive = true")
    List<Unit> getActive();


    @Modifying
    @Query("UPDATE Unit u SET u.isActive = ?1 WHERE u.id = ?2")
    void update(boolean bool, Long id);


    @Query("SELECT u FROM Unit u WHERE u.id = ?1")
    Unit get(Long id);
}