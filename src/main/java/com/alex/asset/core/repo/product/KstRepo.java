package com.alex.asset.core.repo.product;

import com.alex.asset.core.domain.fields.constants.KST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KstRepo extends JpaRepository<KST, Long> {


    @Query("SELECT k FROM KST k WHERE k.num LIKE :prefix%")
    List<KST> findByNum(@Param("prefix") String prefix);


    @Query("SELECT k FROM KST k WHERE k.id = ?1")
    KST get(Long id);
}
