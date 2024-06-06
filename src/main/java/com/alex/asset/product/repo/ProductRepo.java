package com.alex.asset.product.repo;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
            "WHERE p.isActive = ?1  AND p.isScrapping = ?2 " +
            "ORDER BY p.id DESC")
    List<Product> getProductsListByAdmin(Boolean isActive, Boolean isScraped);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.isScrapping = ?1 AND p.isActive = true " +
            "ORDER BY p.id DESC")
    List<Product> getProductsListByEmp(Boolean isScraped);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.warrantyPeriod BETWEEN :startDate AND :endDate " +
            "AND p.isActive = true AND p.isScrapping != true " +
            "ORDER BY p.id DESC")
    List<Product> getProductsByWarrantyPeriod(@Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.inspectionDate BETWEEN :startDate AND :endDate " +
            "AND p.isActive = true AND p.isScrapping != true " +
            "ORDER BY p.id DESC")
    List<Product> getProductsByInspectionPeriod(@Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);




    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.id = ?1")
    Product get(Long id);


    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE (LOWER(p.title) LIKE CONCAT('%', LOWER(:prefix), '%') OR " +
            "LOWER(p.barCode) LIKE CONCAT('%', LOWER(:prefix), '%') OR " +
            "LOWER(p.rfidCode) LIKE CONCAT('%', LOWER(:prefix), '%')) " +
            " AND p.isScrapping = :is_scrapped " +
            " AND p.isActive = true")
    List<Product> getByKeyWord(
            @Param("prefix") String prefix,
            @Param("is_scrapped") Boolean isScrapped
    );




    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.barCode = ?1 AND p.isActive = true ")
    Optional<Product> getByBarCode(String barCode);



    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.rfidCode = ?1 AND p.isActive = true ")
    Optional<Product> getByRfidCode(String rfidCode);


    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.branch = ?1 " +
            "AND p.isActive = true " +
            "AND (p.isScrapping IS NULL OR p.isScrapping = false)")
    List<Product> findAllByBranch(Branch branch);

    boolean existsByBarCode(String barCode);
    boolean existsByRfidCode(String rfidCode);
    boolean existsByInventoryNumber(String inventoryNumber);
    boolean existsBySerialNumber(String serialNumber);


    @Query("SELECT p FROM Product p WHERE (:barCode IS NULL OR p.barCode = :barCode) " +
            "AND (:rfidCode IS NULL OR p.rfidCode = :rfidCode) " +
            "AND (:inventoryNumber IS NULL OR p.inventoryNumber = :inventoryNumber) " +
            "AND (:serialNumber IS NULL OR p.serialNumber = :serialNumber) " +
            "AND p.isActive = true ")
    Optional<Product> findByUniqueValues(String barCode, String rfidCode, String inventoryNumber, String serialNumber);



    @Query(value = "SELECT COUNT(*) " +
            "FROM Product sp " +
            "WHERE sp.branch.id = :branchId AND sp.isActive = true ")
    int countProductsByBranchId(@Param("branchId") Long branchId);
}
