package com.alex.asset.a_security.repo;

import com.alex.asset.a_security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
