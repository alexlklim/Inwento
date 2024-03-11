package com.alex.asset.invents.controller;

import com.alex.asset.invents.dto.InventV1Representation;
import com.alex.asset.invents.dto.InventDto;
import com.alex.asset.invents.service.InventService;
import com.alex.asset.product.dto.ProductDto;
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

import java.util.List;

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
    public ResponseEntity<InventDto> getInventById(@PathVariable("id") Long inventId) {
        log.info(TAG + "Get invent with id {}", inventId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Check if any invent is active at this time, it returns short representation of active or last invent")
    @GetMapping("/active")
    public ResponseEntity<InventV1Representation> isInventActive() {
        // check if any invent is active at this time and return info about active invent or last invent
        // today is a day between start and finish
        InventV1Representation invent = inventService.isAnyInventActive();
        return new ResponseEntity<>(invent, HttpStatus.OK);
    }

    @Operation(summary = "Start new inventarization")
    @Secured("ROLE_ADMIN")
    @GetMapping("/start")
    public ResponseEntity<HttpStatus> startInvent(@RequestBody InventDto dto, Authentication authentication) {
        log.info(TAG + "Start invent");
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = inventService.startInvent(principal.getUserId(), dto);

        // check if other invent is not active at this time
        // create new invent by this user
        // send notification for all user that invent is started

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Finish inventarization")
    @Secured("ROLE_ADMIN")
    @PutMapping("/finish")
    public ResponseEntity<List<ProductDto>> finishInvent() {
        log.info(TAG + "Finish invent");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Change visibility of invent")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    public ResponseEntity<List<ProductDto>> changeVisibilityOfInvent(@RequestBody DtoActive dto) {
        log.info(TAG + "Finish invent");

        // event should be not finished

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
