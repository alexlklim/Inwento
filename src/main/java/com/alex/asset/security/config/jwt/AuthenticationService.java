package com.alex.asset.security.config.jwt;

import com.alex.asset.email.EmailService;
import com.alex.asset.security.domain.Token;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.dto.AuthDto;
import com.alex.asset.security.dto.LoginDto;
import com.alex.asset.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final String TAG = "AUTHENTICATION SERVICE - ";

    private final UserAuthService userAuthService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final EmailService emailService;


    public AuthDto authenticate(LoginDto request) {
        log.info(TAG + "Try to authenticate user with email: {}", request.getEmail());
        if (!userAuthService.existsByEmail(request.getEmail())) {
            log.error(TAG + "User with username {} doesnt exists", request.getEmail());
            return null;
        }
        User user = userAuthService.getByEmail(request.getEmail());
        if (!user.isEnabled()) {
            log.error(TAG + "User with username {} doesnt enabled", request.getEmail());
            return null;
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            log.info(TAG + "Authentication success for user with email {}", request.getEmail());
        } catch (AuthenticationException e) {
            log.error(TAG + "Authentication failed for user with email {}", request.getEmail());
            return null;
        }

        return getAuthDto(user);
    }

    private AuthDto getAuthDto(User user){
        log.info(TAG + "Get authentication dto for user with email: {}", user.getEmail());
        CustomPrincipal principal = new CustomPrincipal(user);
        String accessToken = jwtService.generateToken(principal);
        Token refreshToken = tokenService.createRefreshToken(user);
        AuthDto authDto = new AuthDto();
        authDto.setFirstName(user.getFirstname());
        authDto.setLastName(user.getLastname());
        authDto.setExpiresAt(tokenService.getTokenById(refreshToken.getId()).getExpired());
        authDto.setAccessToken(accessToken);
        authDto.setRefreshToken(refreshToken.getToken());
        authDto.setRole(Collections.singletonList(user.getRoles().name()));
        return authDto;
    }

    public AuthDto refreshToken(TokenDto request) {
        log.info(TAG + "Refresh access and refresh tokens for user: {}", request.getEmail());
        User user = userAuthService.getByEmail(request.getEmail());

        if (user == null) {
            log.error(TAG + "User with email {} not found", request.getEmail());
            return null;
        }

        // check if token belong to this user and not expired
        boolean result =  tokenService.checkIfTokenValid(request.getRefreshToken(), user);
        if (!result) {
            log.info(TAG + "Token is not valid {}", request.getRefreshToken());
            return null;
        }
        // if token valid -> delete old tokens
        tokenService.deleteTokenByUser(user);
        return getAuthDto(user);
    }

    public boolean forgotPasswordAction(String email) {
        log.info(TAG + "Forgot password action: {}", email);

        User user = userAuthService.getByEmail(email);
        if (user == null) {
            log.error(TAG + "User with email {} not found", email);
            return false;
        }

        tokenService.deleteTokenByUser(user);
        emailService.forgotPassword(tokenService.createRefreshToken(user).getId().toString(), user.getEmail());
        return true;
    }

    public boolean recoveryPassword(String token, String password) {
        log.info(TAG + "Recovery password for user with token: {}", token);
        Token tokenFromDB = tokenService.getTokenByToken(UUID.fromString(token));
        if (tokenFromDB == null) {
            log.error(TAG + "Token from DB is null for token {} ", token);
            return false;
        }
        User user = userAuthService.getById(tokenFromDB.getUser().getId());
        tokenService.deleteTokenByUser(user);
        userAuthService.changePasswordForgot(user.getEmail(), password);
        return true;
    }

    @Transactional
    public void logout(CustomPrincipal principal) {
        log.info(TAG + "Logout user with email {}", principal.getName());
        User user = userAuthService.getByEmail(principal.getName());
        tokenService.deleteTokenByUser(user);
        SecurityContextHolder.clearContext();
    }
}
