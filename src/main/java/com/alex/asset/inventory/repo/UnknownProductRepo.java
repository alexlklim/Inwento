package com.alex.asset.inventory.repo;

import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.UnknownProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UnknownProductRepo extends JpaRepository<UnknownProduct, Long> {

    boolean existsByCode(String code);


    boolean existsByCodeAndEvent(String barCode, Event event);

    List<UnknownProduct> findByEvent(Event event);


    Optional<UnknownProduct> findByCode(String code);

    Optional<UnknownProduct> findByCodeAndEvent(String code, Event event);

    List<UnknownProduct> findAllByEvent(Event event);

    @Query(value = "SELECT COUNT(*) FROM UnknownProduct up WHERE up.event.id = :eventId")
    int countProductsByEventId(@Param("eventId") Long eventId);
}
