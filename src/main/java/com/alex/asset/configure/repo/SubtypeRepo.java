package com.alex.asset.configure.repo;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Subtype;
import com.alex.asset.configure.domain.Type;
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


    Optional<Subtype> findSubtypeBySubtypeAndType(String subtype, Type type);

    boolean existsBySubtypeAndType(String location, Type type);
}
