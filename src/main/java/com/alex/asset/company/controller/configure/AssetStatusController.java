package com.alex.asset.company.controller.configure;

import com.alex.asset.company.service.ConfigureService;
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
@RequestMapping("/api/core/company/asset-status")
@Tag(name = "AssetStatus Controller", description = "Asset API")
public class AssetStatusController {
    private final String TAG = "ASSEt_STATUS_CONTROLLER - ";

    private final ConfigureService configureService;

    @Operation(summary = "update asset status, change their visibility by id")
    @PutMapping
    public ResponseEntity<HttpStatus> updateAssetStatuses(@RequestBody List<DtoActive> DTOs){
        log.info(TAG + "Try to update asset statuses");
        configureService.updateAssetStatuses(DTOs);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
