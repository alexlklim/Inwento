package com.alex.asset.security.controller;


import com.alex.asset.security.UserService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.security.config.jwt.UserAuthService;
import com.alex.asset.security.dto.RegisterDto;
import com.alex.asset.security.dto.UserDto;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/company/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final String TAG = "USER_CONTROLLER - ";

    private final UserAuthService userAuthService;
    private final UserService userService;


    @Operation(summary = "Register new user")
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<UserDto> register(
            @RequestBody RegisterDto request,
            Authentication authentication) {
        log.info(TAG + "Register new user");
        userAuthService.register(request, ((CustomPrincipal) authentication.getPrincipal()).getUserId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Get info about all user by id (only for admin)")
    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info(TAG + "Get all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @Operation(summary = "Change user visibility (only for admin)")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    public ResponseEntity<HttpStatus> changeUserVisibility(
            @RequestBody DtoActive dto,
            Authentication authentication) {
        log.info(TAG + "Try to change user visibility");
        userService.changeUserVisibility(
                dto,
                ((CustomPrincipal) authentication.getPrincipal()).getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get info about user by id (only for admin)")
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getInfoAboutUser(
            @PathVariable("id") Long userId) {
        log.info(TAG + "Get info about user with id {}", userId);
        return new ResponseEntity<>(userService.getInfoAboutUserById(userId), HttpStatus.OK);
    }
    @Operation(summary = "Update info about user by id (only for admin)")
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateInfoAboutUser(
            @PathVariable("id") Long id,
            @RequestBody UserDto dto,
            Authentication authentication) {
        log.info(TAG + "Update info about user with id {}", id);
        return new ResponseEntity<>(
                userService.updateUser(id, dto, ((CustomPrincipal) authentication.getPrincipal()).getUserId()),
                HttpStatus.OK);
    }


}
