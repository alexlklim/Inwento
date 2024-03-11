package com.alex.asset.invents.repo;

import com.alex.asset.invents.domain.Invent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface InventRepo extends JpaRepository<Invent, Long> {

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END " +
            "FROM Invent i " +
            "WHERE i.startDate <= :now AND i.finishDate >= :now AND i.isActive = true")
    boolean isInventNow(@Param("now") LocalDate now);
}
