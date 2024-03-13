package com.alex.asset.configure.controllers;


import com.alex.asset.configure.domain.KST;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/kst")
@Tag(name = "KST Controller", description = "KST API")
public class KstController {
    private final String TAG = "KST_CONTROLLER - ";

    private final ConfigureService configureService;

    @Operation(summary = "Get all KST (active and not)")
    @GetMapping
    public ResponseEntity<List<KST>> getAllKST() {
        log.info(TAG + "Try to get all KST");
        return new ResponseEntity<>(
                configureService.getAllKSTs(),
                HttpStatus.OK);
    }

    @Operation(summary = "Get all active KST by KST num")
    @GetMapping("/{num}")
    public ResponseEntity<List<KST>> getKSTByNum(
            @PathVariable("num") String num) {
        log.info(TAG + "Try to get KST by num");
        if (num.length() < 2)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(
                configureService.getKSTsByNum(num),
                HttpStatus.OK);
    }

    @Operation(summary = "Update visibility of KSTs by id and its new status")
    @PutMapping
    public ResponseEntity<HttpStatus> updateMPKs(
            @RequestBody List<DtoActive> dtoActiveList,
            Authentication authentication) {
        log.info(TAG + "Try to update visibility of KSTs");
        configureService.updateKSTs(
                dtoActiveList,
                ((CustomPrincipal) authentication.getPrincipal()).getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
