package com.alex.asset.core.repo.product;

import com.alex.asset.core.domain.fields.MPK;
import com.alex.asset.core.domain.fields.Subtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MpkRepo extends JpaRepository<MPK, Long> {

    @Query("SELECT m FROM MPK m WHERE m.isActive = true")
    List<MPK> getActive();

    @Modifying
    @Query("UPDATE MPK m SET m.isActive = ?1 WHERE m.id = ?2")
    void update(boolean bool, Long id);
}
