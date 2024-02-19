package com.alex.asset.core.repo.product.type;


import com.alex.asset.core.domain.fields.Subtype;
import com.alex.asset.core.domain.fields.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubtypeRepo extends JpaRepository<Subtype, Long> {

    Optional<Subtype> findBySubtypeAndType(String subtype ,Type type);
    List<Subtype> findByActiveTrueAndType(Type type);
}