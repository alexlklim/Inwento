package com.alex.asset.security.repo;

import com.alex.asset.core.domain.Company;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.id  = ?1")
  User getUser(Long id);

  @Query("SELECT u FROM User u WHERE u.email  = ?1")
  Optional<User> getUserByEmail(String email);

  @Modifying
  @Query("UPDATE User u SET u.isActive = false WHERE u = ?1")
  void makeUserInactive(User user);

  @Modifying
  @Query("UPDATE User u SET u.isActive = true WHERE u = ?1")
  void makeUserActive(User user);

  boolean existsByEmail(String email);



}
