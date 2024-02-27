package com.alex.asset.security.repo;

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

  @Query("SELECT u FROM User u WHERE u.email  = ?1")
  User getUser(String email);

  @Query("SELECT u FROM User u WHERE u.isActive = true")
  List<User> getActiveUsers();

  @Modifying
  @Query("UPDATE User u SET u.isActive = ?1 WHERE u.id = ?2")
  void updateVisibility(boolean bool, Long id);


  boolean existsByEmail(String email);


  Optional<User> getUserByEmail(String username);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE :role IN (u.roles) AND u.id = :id")
  boolean checkIfRole(@Param("role") String role, @Param("id") Long id);

}
