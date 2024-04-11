package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.MPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MpkRepo extends JpaRepository<MPK, Long> {

    @Query("SELECT m FROM MPK m WHERE m.isActive = true")
    List<MPK> getActive();

    @Modifying
    @Query("UPDATE MPK m SET m.isActive = ?1 WHERE m.id = ?2")
    void update(boolean bool, Long id);

    @Query("SELECT m FROM MPK m WHERE m.id = ?1")
    MPK get(Long id);

    boolean existsByMpk(String name);

    Optional<MPK> findMPKByMpk(String mpk);

}
