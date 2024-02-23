package com.alex.asset.security.config.jwt;

import com.alex.asset.security.domain.Token;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.TokenRepo;
import com.alex.asset.utils.DateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Transactional
@Slf4j @RequiredArgsConstructor @Service
public class TokenService {
    private final TokenRepo tokenRepo;

    public void deleteTokenByUser(User user) {
        log.info("Delete refresh token for user with email: {}", user.getEmail());
        tokenRepo.deleteAllByUser(user);
    }

    public Token getTokenById(Long tokenID){
        return tokenRepo.findById(tokenID).orElse(null);
    }

    public Token createRefreshToken(User user){
        log.info("Create refresh token for user with email: {}", user.getEmail());
        tokenRepo.deleteAllByUser(user);
        Token token = new Token();
        token.setUser(user);
        token.setToken(UUID.randomUUID());
        token.setCreated(DateService.getDateNow());
        token.setExpired(DateService.addOneDayToDate(DateService.getDateNow()));
        return tokenRepo.save(token);
    }


    public boolean checkIfTokenValid(UUID refreshToken, User user) {
        log.info("Check if token belong to user: {} and not expired", user.getEmail());
        Optional<Token> optionalToken = tokenRepo.findByUserAndToken(user, refreshToken);

        if (optionalToken.isEmpty()) return false;

        // check if token expired
        Token token = optionalToken.get();
        return token.getExpired() != null && token.getExpired().isBefore(LocalDateTime.now());
    }


    public Token getTokenByToken(UUID refreshToken) {
        return tokenRepo.findByToken(refreshToken);
    }
}
