package com.alex.asset.invents.repo;

import com.alex.asset.invents.domain.Invent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventRepo extends JpaRepository<Invent, Long> {
}
