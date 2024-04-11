package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepo extends JpaRepository<Type, Long> {

    @Query("SELECT t FROM Type t WHERE t.isActive = true")
    List<Type> getActive();

    boolean existsByType(String type);

    Optional<Type> findTypeByType(String type);
}
