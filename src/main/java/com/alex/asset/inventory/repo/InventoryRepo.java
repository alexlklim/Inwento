package com.alex.asset.inventory.repo;

import com.alex.asset.inventory.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {


    @Query("SELECT i FROM Inventory i WHERE i.isActive = true " +
            "AND i.startDate <= :currentDate " +
            "AND i.isFinished = false ")
    Optional<Inventory> getCurrentInvent(LocalDate currentDate);
}
