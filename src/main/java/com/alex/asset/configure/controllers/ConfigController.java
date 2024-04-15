package com.alex.asset.configure.controllers;

import com.alex.asset.configure.domain.AssetStatus;
import com.alex.asset.configure.domain.KST;
import com.alex.asset.configure.domain.MPK;
import com.alex.asset.configure.domain.Unit;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.dto.DtoName;
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
@RequestMapping("/api/v1/company")
@Tag(name = "Config Controller", description = "Config API")
public class ConfigController {
    private final String TAG = "CONFIG_CONTROLLER - ";
    private final ConfigureService configureService;


    @Operation(summary = "get asset statuses (active or not)")
    @GetMapping("/asset-status")
    @ResponseStatus(HttpStatus.OK)
    public List<AssetStatus> getAssetStatuses() {
        log.info(TAG + "Try to update asset statuses");
        return configureService.getAllAssetStatuses();
    }


    @Operation(summary = "update asset status, change their visibility by id")
    @PutMapping("/asset-status")
    @ResponseStatus(HttpStatus.OK)
    public void updateAssetStatuses(
            @RequestBody List<DtoActive> listActiveDto) {
        log.info(TAG + "Try to update asset statuses");
        configureService.updateAssetStatuses(
                listActiveDto,
                SecHolder.getUserId());
    }


    @Operation(summary = "get all units (active or not)")
    @GetMapping("/unit")
    @ResponseStatus(HttpStatus.OK)
    public List<Unit> getUnits() {
        log.info(TAG + "Get all units");
        return configureService.getAllUnits();
    }

    @Operation(summary = "Update units, send id of unit and it's new status: active or not)")
    @PutMapping("/unit")
    @ResponseStatus(HttpStatus.OK)
    public void updateUnits(
            @RequestBody List<DtoActive> dtoActiveList) {
        log.info(TAG + "Try to update units");
        configureService.updateUnits(
                dtoActiveList,
                SecHolder.getUserId());
    }


    @Operation(summary = "Get all KST (active and not)")
    @GetMapping("/kst")
    @ResponseStatus(HttpStatus.OK)
    public List<KST> getAllKST() {
        log.info(TAG + "Try to get all KST");
        return configureService.getAllKSTs();
    }

    @Operation(summary = "Get all active KST by KST num")
    @GetMapping("/kst/{num}")
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
    @PutMapping("/kst")
    @ResponseStatus(HttpStatus.OK)
    public void updateKSTs(
            @RequestBody List<DtoActive> dtoActiveList) {
        log.info(TAG + "Try to update visibility of KSTs");
        configureService.updateKSTs(
                dtoActiveList,
                SecHolder.getUserId());
    }



    @Operation(summary = "Get all active MPK")
    @GetMapping("/mpk")
    @ResponseStatus(HttpStatus.OK)
    public List<MPK> getAllMPKs() {
        log.info(TAG + "Try to get all MPK");
        return configureService.getMPKs();
    }

    @Operation(summary = "Add new MPK")
    @PostMapping("/mpk")
    @ResponseStatus(HttpStatus.CREATED)
    public MPK addMPK(
            @RequestBody DtoName dtoName) {
        log.info(TAG + "Try to add MPK");
        return configureService.addMPK(
                dtoName,
                SecHolder.getUserId());
    }

    @Operation(summary = "Update MPK visibility by id")
    @PutMapping("/mpk")
    @ResponseStatus(HttpStatus.OK)
    public void updateMPKs(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Try to update MPK");
        configureService.updateMPK(
                dtoActive,
                SecHolder.getUserId());
    }
}
