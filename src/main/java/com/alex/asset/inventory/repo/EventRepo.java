package com.alex.asset.inventory.repo;

import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findAllByUserAndInventoryOrderByCreatedDesc(User user, Inventory inventory);

    List<Event> findAllByInventoryOrderByCreatedDesc(Inventory inventory);
}
