package com.alex.asset.security.repo;

import com.alex.asset.security.domain.Token;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepo extends JpaRepository<Token, Long> {

  Optional<Token> findByIdAndUser(Long token, User user);


  void deleteAllByUser(User user);


  Optional<Token> findByUserAndToken(User user, UUID token);

  Token findByToken(UUID token);
}
