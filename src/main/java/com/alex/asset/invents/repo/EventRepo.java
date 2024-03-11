package com.alex.asset.invents.repo;

import com.alex.asset.invents.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {
}
