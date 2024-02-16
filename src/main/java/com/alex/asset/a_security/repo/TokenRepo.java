package com.alex.asset.a_security.repo;

import com.alex.asset.a_security.domain.Token;
import com.alex.asset.a_security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepo extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(UUID token);
    Optional<Token> findByUser(User user);



}
