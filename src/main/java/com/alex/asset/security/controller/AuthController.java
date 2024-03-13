package com.alex.asset.security.controller;

import com.alex.asset.security.config.jwt.AuthenticationService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.security.config.jwt.UserAuthService;
import com.alex.asset.security.dto.AuthDto;
import com.alex.asset.security.dto.LoginDto;
import com.alex.asset.security.dto.PasswordDto;
import com.alex.asset.security.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Auth API")
public class AuthController {
    private final String TAG = "AUTHENTICATION_CONTROLLER - ";

    private final UserAuthService userAuthService;
    private final AuthenticationService authenticationService;


    @Operation(summary = "Login user and get Auth DTO")
    @PostMapping("/login")
    public ResponseEntity<AuthDto> authenticate(@RequestBody LoginDto request) {
        AuthDto authDto = authenticationService.authenticate(request);
        if (authDto == null) {
            log.error(TAG + "Authentication for user {} failed", request.getEmail());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info(TAG + "Authentication for user {} success", request.getEmail());
        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }

    @Operation(summary = "logout user, inactive refresh token")
    @GetMapping("/logout")
    public ResponseEntity<AuthDto> logout(Authentication authentication) {
        authenticationService.logout((CustomPrincipal) authentication.getPrincipal());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "refresh token and get new Auth DTO")
    @PostMapping("/refresh")
    public ResponseEntity<AuthDto> refreshToken(@RequestBody TokenDto request) {
        AuthDto authDto = authenticationService.refreshToken(request);
        if (authDto == null) {
            log.error(TAG + "Refresh token for user {} failed", request.getEmail());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }


    @Operation(summary = "change password for authenticated users")
    @PostMapping("/pw/change")
    public ResponseEntity<AuthDto> changePassword(@RequestBody PasswordDto request) {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userAuthService.changePassword(request, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // request which send email with the link with token to recovery password

    @Operation(summary = "send link to email to recovery password")
    @PostMapping("/pw/forgot")
    public ResponseEntity<String> forgotPassword(String email) {
        // send link to restore account to email
        boolean result = authenticationService.forgotPasswordAction(email);
        if (result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "change password")
    @PostMapping("/pw/recovery/{token}")
    public ResponseEntity<?> recoveryPassword(@PathVariable("token") String token, String password) {
        boolean result = authenticationService.recoveryPassword(token, password);
        if (result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
