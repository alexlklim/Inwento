package com.alex.asset.security.repo;

import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.id  = ?1")
  User getUser(Long id);


  @Query("SELECT u FROM User u WHERE u.isActive = true")
  List<User> getActiveUsers();

  @Query("SELECT u FROM User u WHERE u.isActive = true AND u.roles = ?1")
  List<User> getActiveUsersByRole(Role role);

  @Modifying
  @Query("UPDATE User u SET u.isActive = ?1 WHERE u.id = ?2")
  void updateVisibility(boolean bool, Long id);


  boolean existsByEmail(String email);


  Optional<User> getUserByEmail(String username);


}
