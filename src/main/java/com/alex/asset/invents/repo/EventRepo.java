package com.alex.asset.invents.repo;

import com.alex.asset.invents.domain.event.Event;
import com.alex.asset.invents.domain.Invent;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findAllByUserAndInventOrderByCreatedDesc(User user, Invent invent);

    List<Event> findAllByInventOrderByCreatedDesc(Invent invent);
}
