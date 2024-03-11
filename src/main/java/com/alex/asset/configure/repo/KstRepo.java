package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.KST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KstRepo extends JpaRepository<KST, Long> {


    @Query("SELECT k FROM KST k WHERE k.num LIKE :prefix% and k.isActive = true")
    List<KST> findByNum(@Param("prefix") String prefix);

    @Modifying
    @Query("UPDATE KST k SET k.isActive = ?1 WHERE k.id = ?2")
    void update(boolean bool, Long id);

    @Query("SELECT k FROM KST k WHERE k.id = ?1")
    KST get(Long id);
}