package com.alex.asset.inventory.repo;

import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.UnknownProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnknownProductRepo extends JpaRepository<UnknownProduct, Long> {

    boolean existsByCode(String code);


    boolean existsByCodeAndEvent(String barCode, Event event);

    List<UnknownProduct> findByEvent(Event event);
}
