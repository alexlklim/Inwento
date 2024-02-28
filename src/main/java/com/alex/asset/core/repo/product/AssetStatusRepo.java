package com.alex.asset.core.repo.product;

import com.alex.asset.core.domain.fields.constants.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AssetStatusRepo extends JpaRepository<AssetStatus, Long> {


    @Query("SELECT a FROM AssetStatus a WHERE a.isActive = true")
    List<AssetStatus> getActive();


    @Modifying
    @Query("UPDATE AssetStatus a SET a.isActive = ?1 WHERE a.id = ?2")
    void update(boolean bool, Long id);

    @Query("SELECT a FROM AssetStatus a WHERE a.id = ?1")
    AssetStatus get(Long id);

}
