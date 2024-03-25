package com.alex.asset.product.controller;


import com.alex.asset.product.dto.ProductDto;
import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.dto.ProductV3Dto;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@Tag(name = "Product Controller", description = "Product API")
public class ProductController {
    private final String TAG = "PRODUCT_CONTROLLER - ";

    private final ProductService productService;

    @Operation(summary = "Get products (only active)")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getActiveProducts() {
        log.info(TAG + "Try to get all active products");
        return new ResponseEntity<>(
                productService.getActive(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get all products (active and not)")
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info(TAG + "Try to get all products");
        return new ResponseEntity<>(
                productService.getAll(),
                HttpStatus.OK
        );
    }


    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(
            @PathVariable("id") Long id) {
        log.info(TAG + "Try to get product by id {}", id);
        return new ResponseEntity<>(
                productService.getById(id),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get short product by id (for android app")
    @GetMapping("/app/{id}")
    public ResponseEntity<ProductV3Dto> getShortProductById(
            @PathVariable("id") Long id) {
        log.info(TAG + "Try to get product by id {}", id);
        return new ResponseEntity<>(
                productService.getShortProductById(id),
                HttpStatus.OK
        );
    }


    @Operation(summary = "Add new product")
    @PostMapping
    public ResponseEntity<ProductDto> addNewProduct(
            @RequestBody ProductDto productDto) {
        log.info(TAG + "get all products for company");
        return new ResponseEntity<>(
                productService.create(
                        productDto,
                        SecHolder.getUserId()
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update product by id and its new active status")
    @PutMapping
    public ResponseEntity<?> updateProduct(
            @RequestBody Map<String, Object> updates) {
        log.info(TAG + "Update product");
        productService.updateProduct(
                SecHolder.getUserId(),
                updates
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Update visibility fo product by id and its new active status")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    public ResponseEntity<HttpStatus> updateVisibilityOfProduct(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Update product visibility");
        productService.updateVisibility(
                dtoActive,
                SecHolder.getUserId()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Get short products by title or bar code")
    @GetMapping("/filter/{key_word}")
    public ResponseEntity<Map<Long, String>> getProductsByTitleOrBarCode(
            @PathVariable("key_word") String keyWord) {
        log.info(TAG + "Try to get all products by key word {}", keyWord);
        return new ResponseEntity<>(
                productService.getByTitleOrBarCode(keyWord),
                HttpStatus.OK
        );
    }


    @Operation(summary = "Get product history by id")
    @GetMapping("/history/{id}")
    public ResponseEntity<List<ProductHistoryDto>> getHistoryOfProductById(
            @PathVariable("id") Long productId) {
        log.info(TAG + "Get history of product");
        return new ResponseEntity<>(
                productService.getHistoryOfProductById(productId),
                HttpStatus.OK
        );
    }


}
