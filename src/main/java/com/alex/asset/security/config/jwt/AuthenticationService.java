package com.alex.asset.security.config.jwt;

import com.alex.asset.email.EmailService;
import com.alex.asset.security.domain.Token;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.domain.dto.AuthDto;
import com.alex.asset.security.domain.dto.LoginDto;
import com.alex.asset.security.domain.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserAuthService userAuthService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final EmailService emailService;


    public AuthDto authenticate(LoginDto request) {
        log.info("Try to authenticate user with email: {}", request.getEmail());
        if (!userAuthService.existsByEmail(request.getEmail())) {
            log.error("User with username {} doesnt exists", request.getEmail());
            return null;
        }
        User user = userAuthService.getByEmail(request.getEmail());
        if (!user.isEnabled()) {
            log.error("User with username {} doesnt enabled", request.getEmail());
            return null;
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            log.info("Authentication success for user with email {}", request.getEmail());
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user with email {}", request.getEmail());
            return null;
        }

        return getAuthDto(user);
    }

    private AuthDto getAuthDto(User user){
        log.info("Get authentication dto for user with email: {}", user.getEmail());
        CustomPrincipal principal = new CustomPrincipal(user);
        String accessToken = jwtService.generateToken(principal);
        Token refreshToken = tokenService.createRefreshToken(user);

        AuthDto authDto = new AuthDto();
        authDto.setName(user.getFirstname() + " " + user.getLastname());

        authDto.setExpiresAt(tokenService.getTokenById(refreshToken.getId()).getExpired());
        authDto.setAccessToken(accessToken);
        authDto.setRefreshToken(refreshToken.getToken());
        authDto.setRole(Collections.singletonList(user.getRoles().name()));
        return authDto;
    }

    public AuthDto refreshToken(TokenDto request) {
        log.info("Refresh access and refresh tokens for user: {}", request.getEmail());
        User user = userAuthService.getByEmail(request.getEmail());

        if (user == null) return null;

        // check if token belong to this user and not expired
        boolean result =  tokenService.checkIfTokenValid(request.getRefreshToken(), user);
        if (!result) return null;

        // if token valid -> delete old tokens
        tokenService.deleteTokenByUser(user);
        return getAuthDto(user);
    }

    public boolean forgotPasswordAction(String email) {
        log.info("Forgot password action: {}", email);
        // delete token for this user
        // create new refresh token for this user
        // sent link to restore password with this token
        // return default message
        User user = userAuthService.getByEmail(email);
        if (user == null) return false;



        tokenService.deleteTokenByUser(user);
        emailService.forgotPassword(tokenService.createRefreshToken(user).getId().toString(), user.getEmail());
        return true;
    }

    public boolean recoveryPassword(String token, String password) {
        log.info("Recovery password for user with token: {}", token);
        Token tokenFromDB = tokenService.getTokenByToken(UUID.fromString(token));
        if (tokenFromDB == null) return false;
        User user = userAuthService.getById(tokenFromDB.getUser().getId());
        tokenService.deleteTokenByUser(user);
        userAuthService.changePasswordForgot(user.getEmail(), password);
        return true;
    }
}
