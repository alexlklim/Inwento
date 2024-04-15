package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LocationRepo extends JpaRepository<Location, Long> {


    @Query("SELECT l FROM Location l WHERE l.isActive = true")
    List<Location> getActive();

    @Modifying
    @Query("UPDATE Location l SET l.isActive = ?1 WHERE l.id = ?2")
    void update(boolean bool, Long id);

    Optional<Location> findLocationByLocation(String location);

    @Query("SELECT l FROM Location l WHERE l.location = ?1 and l.branch = ?2")
    Optional<Location> findLocationByLocationAndBranch(String location, Branch branch);

    boolean existsByLocationAndBranch(String location, Branch branch);
}
