package com.alex.asset.product.repo;

import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductHistoryRepo extends JpaRepository<ProductHistory, Long> {


    List<ProductHistory> findAllByProductOrderByCreatedDesc(Product product);
}
