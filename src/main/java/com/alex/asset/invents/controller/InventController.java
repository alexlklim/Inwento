package com.alex.asset.invents.controller;

import com.alex.asset.invents.dto.InventDto;
import com.alex.asset.invents.service.InventService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
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

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/invent")
@Tag(name = "Invent Controller", description = "Invent API")
public class InventController {
    private final String TAG = "INVENT_CONTROLLER - ";

    private final InventService inventService;


    @Operation(summary = "Get invent by id")
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<InventDto> getInventById(
            @PathVariable("id") Long inventId) {
        log.info(TAG + "Get invent with id {}", inventId);
        return new ResponseEntity<>(inventService.getInventById(inventId), HttpStatus.OK);
    }


    @Operation(summary = "Check is invent now or not " +
            "(it will be used in android app (main page) to notify users that invent is started")
    @GetMapping("/active")
    public ResponseEntity<Boolean> isInventActive() {
        log.info(TAG + "Check is inventory taking place now or not");
        return new ResponseEntity<>(inventService.isInventNow(), HttpStatus.OK);
    }

    @Operation(summary = "get current active invent (it returns 404 if no active invent now")
    @GetMapping("/current")
    public ResponseEntity<InventDto> getCurrentEvent() {
        log.info(TAG + "get current invent");
        return new ResponseEntity<>(inventService.getCurrentInvent(), HttpStatus.OK);
    }



    @Operation(summary = "Start new inventarization")
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<HttpStatus> create(
            @RequestBody InventDto dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Create invent");
        inventService.startInvent(principal.getUserId(), dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update inventarization")
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id") Long inventId,
            @RequestBody InventDto dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Update invent");
        inventService.updateInvent(principal.getUserId(), inventId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Change visibility of invent")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    public ResponseEntity<HttpStatus> changeVisibility(
            @RequestBody DtoActive dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Finish invent");
        inventService.changeVisibility(principal.getUserId(), dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
