package com.alex.asset.configure.repo;

import com.alex.asset.configure.domain.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Long> {


    boolean existsByCompany(String company);
    boolean existsByNip(String nip);
    boolean existsByAddress(String address);
}
