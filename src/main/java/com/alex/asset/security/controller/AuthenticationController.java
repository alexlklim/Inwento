package com.alex.asset.security.controller;

import com.alex.asset.security.config.jwt.AuthenticationService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.security.config.jwt.UserService;
import com.alex.asset.security.domain.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final String TAG = "AUTHENTICATION_CONTROLLER - ";


    private final UserService userService;
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto request) {
        boolean registerResult = userService.register(request);
        if (!registerResult) {
            log.error(TAG + "Registration for user {} failed", request.getEmail());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // send email that user was created for this email

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto> authenticate(@RequestBody LoginDto request) {
        AuthDto authDto = authenticationService.authenticate(request);
        if (authDto == null) {
            log.error(TAG + "Authentication for user {} failed", request.getEmail());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDto> refreshToken(@RequestBody TokenDto request) {
        AuthDto authDto = authenticationService.refreshToken(request);
        if (authDto == null) {
            log.error(TAG + "Refresh token for user {} failed", request.getEmail());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }


    @PostMapping("/pw/change")
    public ResponseEntity<AuthDto> changePassword(@RequestBody PasswordDto request) {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean result = userService.changePassword(request, principal);
        if (!result) {
            log.error(TAG + "Change password action for user {} failed", principal.getEmail());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/pw/forgot")
    public ResponseEntity<String> forgotPassword(String email) {
        // send link to restore account to email
        boolean result = authenticationService.forgotPasswordAction(email);
        if (result) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/pw/recovery/{token}")
    public ResponseEntity<?> recoveryPassword(
            @PathVariable("token") String token,
            String password) {
        boolean result = authenticationService.recoveryPassword(token, password);
        if (result) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
