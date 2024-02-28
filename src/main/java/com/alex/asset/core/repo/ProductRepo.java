package com.alex.asset.core.repo;

import com.alex.asset.core.domain.Product;
import com.alex.asset.core.domain.fields.constants.KST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.isActive = true")
    List<Product> getActive();

    @Modifying
    @Query("UPDATE Product p SET p.isActive = ?1 WHERE p.id = ?2")
    void updateVisibility(boolean bool, Long id);

    @Modifying
    @Query("UPDATE Product p SET p.barCode = ?1 WHERE p.id = ?2")
    void updateBarCode(String barcode, Long id);

    @Modifying
    @Query("UPDATE Product p SET p.rfidCode = ?1 WHERE p.id = ?2")
    void updateRfidCode(String rfidCode, Long id);



    @Query("SELECT p FROM Product p WHERE p.id = ?1")
    Product get(Long id);



    @Query("SELECT p FROM Product p WHERE p.title LIKE :prefix%")
    List<Product> getByTitle(@Param("prefix") String prefix);





}
