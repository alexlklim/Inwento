package com.alex.asset.core.repo.product.constatns;

import com.alex.asset.core.domain.fields.constants.KST;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KstRepo extends JpaRepository<KST, Long> {


    Optional<KST> findByKst(String kst);
}
