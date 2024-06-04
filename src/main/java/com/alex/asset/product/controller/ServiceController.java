package com.alex.asset.product.controller;


import com.alex.asset.product.service.ProductService;
import com.alex.asset.product.service.SerProductService;
import com.alex.asset.utils.SecHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/service")
@Tag(name = "Product Controller", description = "Product API")
public class ServiceController {

    private final String TAG = "PRODUCT_CONTROLLER - ";
    private final SerProductService serProductService;


    @Operation(summary = "Send product to service")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> serviceProduct(@RequestBody Map<String, Object> updates) {
        log.info(TAG + "serviceProduct");
        return serProductService.serviceProduct(updates, SecHolder.getUserId());
    }

}
