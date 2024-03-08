package com.alex.asset.security.controller;


import com.alex.asset.company.dto.UserDto;
import com.alex.asset.security.config.jwt.AuthenticationService;
import com.alex.asset.security.config.jwt.UserAuthService;
import com.alex.asset.security.dto.RegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/company/users")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Auth API")
public class UserController {

    private final String TAG = "USER_CONTROLLER - ";

    private final UserAuthService userAuthService;


    @Operation(summary = "Register new user")
    @Secured("ROLE_ADMIN")
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto request) {
        boolean registerResult = userAuthService.register(request);
        if (!registerResult) {
            log.error(TAG + "Registration for user {} failed", request.getEmail());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
