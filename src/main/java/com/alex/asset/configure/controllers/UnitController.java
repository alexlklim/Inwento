package com.alex.asset.configure.controllers;


import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/unit")
@Tag(name = "Unit Controller", description = "Unit API")
public class UnitController {

    private final String TAG = "UNIT_CONTROLLER - ";


    private final ConfigureService configureService;

    @Operation(summary = "Update units, send id of unit and it's new status: active or not)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateUnits(@RequestBody List<DtoActive> DTOs) {
        log.info(TAG + "Try to update units");
        configureService.updateUnits(DTOs);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
