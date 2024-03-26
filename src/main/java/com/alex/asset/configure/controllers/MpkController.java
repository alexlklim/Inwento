package com.alex.asset.configure.controllers;

import com.alex.asset.configure.domain.MPK;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.dto.DtoName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/mpk")
@Tag(name = "MPK Controller", description = "MPK API")
public class MpkController {

    private final String TAG = "FIELDS_CONTROLLER - ";

    private final ConfigureService configureService;

    @Operation(summary = "Get all active MPK")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MPK> getAllMPKs() {
        log.info(TAG + "Try to get all MPK");
        return configureService.getMPKs();
    }

    @Operation(summary = "Add new MPK")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MPK addMPK(
            @RequestBody DtoName dtoName) {
        log.info(TAG + "Try to add MPK");
        return configureService.addMPK(
                dtoName,
                SecHolder.getUserId());
    }

    @Operation(summary = "Update MPK visibility by id")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMPKs(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Try to update MPK");
        configureService.updateMPK(
                dtoActive,
                SecHolder.getUserId());
    }

}
