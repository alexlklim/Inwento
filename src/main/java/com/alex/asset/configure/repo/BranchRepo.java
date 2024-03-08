package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepo extends JpaRepository<Branch, Long> {

    @Query("SELECT b FROM Branch b WHERE b.isActive = true")
    List<Branch> getActive();

    @Modifying
    @Query("UPDATE Branch b SET b.isActive = ?1 WHERE b.id = ?2")
    void update(boolean bool, Long id);


    @Query("SELECT b FROM Branch b WHERE b.id = ?1")
    Branch get(Long id);
}
