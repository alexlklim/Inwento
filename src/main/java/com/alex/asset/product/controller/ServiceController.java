package com.alex.asset.product.controller;


import com.alex.asset.product.service.SerProductService;
import com.alex.asset.utils.SecHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/service")
@Tag(name = "Product Controller", description = "Product API")
public class ServiceController {

    private final String TAG = "SERVICE_CONTROLLER - ";
    private final SerProductService serProductService;


    @Operation(summary = "Send product to service")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> serviceProduct(@RequestBody Map<String, Object> updates) {
        log.info(TAG + "serviceProduct");
        return serProductService.serviceProduct(updates, SecHolder.getUserId());
    }

    @Operation(summary = "Get all serviced assets")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getServicedAssets(
            @RequestParam(required = false) List<String> fields) {
        log.info(TAG + "getServicedProduct");
        return serProductService.getAllServicedAsset(fields, SecHolder.getUserId());
    }
    @Operation(summary = "Get serviced asset by id")
    @GetMapping("/{asset_id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getServicedAssetById(
            @PathVariable("asset_id") Long assetId,
            @RequestParam(required = false) List<String> fields) {
        log.info(TAG + "getServicedAssetById");
        return serProductService.getServicedAssetById(assetId, fields, SecHolder.getUserId());
    }


}
