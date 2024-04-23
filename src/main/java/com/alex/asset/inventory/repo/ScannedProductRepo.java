package com.alex.asset.inventory.repo;

import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScannedProductRepo extends JpaRepository<ScannedProduct, Long> {
    List<ScannedProduct> findAllByEvent(Event event);

    @Query(value = "SELECT COUNT(*) FROM ScannedProduct sp WHERE sp.event.id = :eventId")
    int countScannedProductsByEventId(@Param("eventId") Long eventId);


    boolean existsByProductAndEvent(Product product, Event event);

}
