package com.alex.asset.inventory.repo;

import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScannedProductRepo extends JpaRepository<ScannedProduct, Long> {


    @Query("SELECT COUNT(sp) " +
            "FROM ScannedProduct sp " +
            "WHERE sp.event = ?1 AND sp.isScanned = ?2")
    int countByEventIdAndIsScanned(Event event, boolean isScanned);


    @Query("SELECT sp " +
            "FROM ScannedProduct sp " +
            "WHERE sp.event = ?1 AND sp.isScanned = ?2")
    List<ScannedProduct> findProductsByEventAndIsScanned(Event event, Boolean isScanned);


    Optional<ScannedProduct> findScannedProductByEventAndProduct(Event event, Product product);

    boolean existsByProductAndEvent(Product product, Event event);


    Optional<ScannedProduct> findByProductAndEvent(Product product, Event event);
}
