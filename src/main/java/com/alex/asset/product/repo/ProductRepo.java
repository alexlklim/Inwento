package com.alex.asset.product.repo;

import com.alex.asset.configure.domain.Branch;
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
            "WHERE p.isActive = true " +
            "ORDER BY p.id DESC")
    List<Product> getActive();

    @Query("SELECT COUNT(p) " +
            "FROM Product p " +
            "WHERE p.isActive = true")
    long getActiveProductCount();

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.id = ?1")
    Product get(Long id);


    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE LOWER(p.title) LIKE CONCAT('%', LOWER(:prefix), '%') OR " +
            "LOWER(p.barCode) LIKE CONCAT('%', LOWER(:prefix), '%')")
    List<Product> getByTitleOrBarCode(@Param("prefix") String prefix);

    Optional<Product> getProductByBarCode(String barCode);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.barCode = ?1 AND p.isActive = true ")
    Optional<Product> getByBarCode(String barCode);


    List<Product> findAllByBranch(Branch branch);
}
