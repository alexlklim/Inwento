package com.alex.asset.inventory.repo;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
            "WHERE e.inventory = ?1  AND e.isActive =?2 " +
            "ORDER BY e.created DESC")
    Optional<Event> getEventByIdAndActive(Long eventId, Boolean active);



    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.inventory = ?1 " +
            "ORDER BY e.created DESC")
    List<Event> getAllEventsByInventory(Inventory inventory);

    Optional<Event> findByInventoryAndBranch(Inventory inventory, Branch branch);

    boolean existsByBranchAndInventory(Branch branch, Inventory inventory);
}
