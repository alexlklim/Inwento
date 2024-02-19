package com.alex.asset.security.config.jwt;

import com.alex.asset.security.domain.Token;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.TokenRepo;
import com.alex.asset.utils.DateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j @RequiredArgsConstructor @Service
public class TokenService {
    private final TokenRepo tokenRepo;

    public void deleteTokenByUser(User user) {
        tokenRepo.deleteAllByUser(user);
    }

    public Token getTokenById(UUID uuid){
        return tokenRepo.findById(uuid).orElse(null);
    }

    public Token createToken(User user){
        log.info("Create refresh token for user with email: {}", user.getEmail());
        Token token = new Token().toBuilder()
                .user(user)
                .created(DateService.getDateNow())
                .expired(DateService.addOneDayToDate(DateService.getDateNow()))
                .build();
        return tokenRepo.save(token);
    }


    public boolean checkIfTokenValid(UUID id, User user) {
        log.info("Check if token belong to user: {} and not expired", user.getEmail());
        Optional<Token> optionalToken = tokenRepo.findByIdAndUser(id, user);
        LocalDateTime currentTime = LocalDateTime.now();
        if (optionalToken.isEmpty()) return false;

        Token token = optionalToken.get();
        return token.getExpired() != null && token.getExpired().isBefore(currentTime);
    }


}
