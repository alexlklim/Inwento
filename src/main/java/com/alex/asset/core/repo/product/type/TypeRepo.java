package com.alex.asset.core.repo.product.type;

import com.alex.asset.core.domain.fields.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TypeRepo extends JpaRepository<Type, Long> {

    @Query("SELECT t FROM Type t WHERE t.isActive = true")
    List<Type> getActive();

    @Query("SELECT t FROM Type t WHERE t.id = ?1")
    Type get(Long id);


    @Modifying
    @Query("UPDATE Type t SET t.isActive = ?1 WHERE t.id = ?2")
    void updateVisibility(boolean bool, Long id);




    boolean existsByType(String type);
}
