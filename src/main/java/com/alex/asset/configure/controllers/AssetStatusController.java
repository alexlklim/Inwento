package com.alex.asset.configure.controllers;

import com.alex.asset.configure.domain.AssetStatus;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
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
@RequestMapping("/api/v1/company/asset-status")
@Tag(name = "AssetStatus Controller", description = "Asset API")
public class AssetStatusController {
    private final String TAG = "ASSEt_STATUS_CONTROLLER - ";

    private final ConfigureService configureService;


    @Operation(summary = "get asset statuses (active or not)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AssetStatus> getAssetStatuses() {
        log.info(TAG + "Try to update asset statuses");
        return configureService.getAllAssetStatuses();
    }


    @Operation(summary = "update asset status, change their visibility by id")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAssetStatuses(
            @RequestBody List<DtoActive> listActiveDto) {
        log.info(TAG + "Try to update asset statuses");
        configureService.updateAssetStatuses(
                listActiveDto,
                SecHolder.getUserId());
    }

}
