package com.alex.asset.inventory.repo;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.isActive = true AND e.user = ?1 AND e.inventory = ?2 " +
            "ORDER BY e.created DESC")
    List<Event> findAllByUserAndInventory(User user, Inventory inventory);


    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.isActive = true AND e.inventory = ?1 " +
            "ORDER BY e.created DESC")
    List<Event> findAllByInventory(Inventory inventory);


    @Query("SELECT e.products FROM Event e WHERE e.branch = ?1")
    List<Product> findProductsByBranch(Branch branch);

    boolean existsByUserAndBranchAndInventory(User user, Branch branch, Inventory currentInventory);
}
