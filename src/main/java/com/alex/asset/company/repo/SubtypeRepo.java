package com.alex.asset.company.repo;


import com.alex.asset.company.domain.Subtype;
import com.alex.asset.company.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtypeRepo extends JpaRepository<Subtype, Long> {


    @Query("SELECT s FROM Subtype s WHERE s.isActive = true")
    List<Subtype> getActive();


    @Query("SELECT s FROM Subtype s WHERE s.isActive = true and s.type = ?1")
    List<Subtype> findByActiveTrueAndType(Type type);


    @Modifying
    @Query("UPDATE Subtype s SET s.isActive = ?1 WHERE s.id = ?2")
    void updateVisibility(boolean bool, Long id);


    @Query("SELECT s FROM Subtype s WHERE s.id = ?1")
    Subtype get(Long id);

}
