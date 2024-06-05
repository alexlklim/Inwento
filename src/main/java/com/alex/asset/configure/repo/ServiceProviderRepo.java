package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.ServiceProvider;
import com.alex.asset.configure.domain.Subtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Long> {


    boolean existsByCompany(String company);
    boolean existsByNip(String nip);
    boolean existsByAddress(String address);

    @Query("SELECT s FROM ServiceProvider s WHERE s.isActive = true")
    List<ServiceProvider> getActive();
}
