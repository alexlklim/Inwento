package com.alex.asset.product.repo;

import com.alex.asset.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.isActive = true")
    List<Product> getActive();


    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.id = ?1")
    Product get(Long id);


    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.title LIKE :prefix%")
    List<Product> getByTitle(@Param("prefix") String prefix);


    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.barCode = ?1 AND p.isActive = true ")
    Optional<Product> getByBarCode(String barCode);


}
