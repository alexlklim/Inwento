package com.alex.asset.core.repo.product.type;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.fields.Branch;
import com.alex.asset.core.domain.fields.Subtype;
import com.alex.asset.core.domain.fields.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubtypeRepo extends JpaRepository<Subtype, Long> {


    @Query("SELECT s FROM Subtype s WHERE s.isActive = true")
    List<Subtype> getActive();


    @Query("SELECT s FROM Subtype s WHERE s.isActive = true and s.type = ?1")
    List<Subtype> findByActiveTrueAndType(Type type);
}
