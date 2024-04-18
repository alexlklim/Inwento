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



    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.isActive = true AND p.isScrapping = false " +
            "ORDER BY p.id DESC")
    List<Product> getActiveWithoutScrapped();




    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.isActive = true " +
            "ORDER BY p.id DESC")
    List<Product> getAllProducts();




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
            "LOWER(p.title) LIKE CONCAT('%', LOWER(:prefix), '%') OR " +
            "LOWER(p.rfidCode) LIKE CONCAT('%', LOWER(:prefix), '%')")
    List<Product> getByKeyWord(@Param("prefix") String prefix);



    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.barCode = ?1 AND p.isActive = true ")
    Optional<Product> getByBarCode(String barCode);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.rfidCode = ?1 AND p.isActive = true ")
    Optional<Product> getByRfidCode(String rfidCode);

    List<Product> findAllByBranch(Branch branch);

    boolean existsByBarCode(String barCode);
    boolean existsByRfidCode(String rfidCode);
    boolean existsByInventoryNumber(String inventoryNumber);
    boolean existsBySerialNumber(String serialNumber);


    @Query("SELECT p FROM Product p WHERE (:barCode IS NULL OR p.barCode = :barCode) " +
            "AND (:rfidCode IS NULL OR p.rfidCode = :rfidCode) " +
            "AND (:inventoryNumber IS NULL OR p.inventoryNumber = :inventoryNumber) " +
            "AND (:serialNumber IS NULL OR p.serialNumber = :serialNumber)")
    Optional<Product> findByUniqueValues(String barCode, String rfidCode, String inventoryNumber, String serialNumber);



    @Query(value = "SELECT COUNT(*) FROM Product sp WHERE sp.branch.id = :branchId")
    int countProductsByBranchId(@Param("branchId") Long branchId);
}
