package com.alex.asset.security.repo;

import com.alex.asset.core.domain.Company;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);


  List<User> findByCompany(Company company);


  @Query("SELECT u FROM User u WHERE u.id  = ?1")
  User getUser(Long id);
}
