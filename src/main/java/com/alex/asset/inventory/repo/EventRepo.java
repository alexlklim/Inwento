package com.alex.asset.inventory.repo;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EventRepo extends JpaRepository<Event, Long> {

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.isActive = true AND e.inventory = ?1 " +
            "ORDER BY e.created DESC")
    List<Event> getActiveEventsByInventory(Inventory inventory);

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.inventory = ?1 " +
            "ORDER BY e.created DESC")
    List<Event> getAllEventsByInventory(Inventory inventory);


    boolean existsByBranchAndInventory(Branch branch, Inventory inventory);
}
