package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.KST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KstRepo extends JpaRepository<KST, Long> {


    @Query("SELECT k FROM KST k WHERE k.num LIKE :prefix% and k.isActive = true")
    List<KST> findByNum(@Param("prefix") String prefix);


    @Query("SELECT k FROM KST k WHERE k.id = ?1")
    KST get(Long id);
}
