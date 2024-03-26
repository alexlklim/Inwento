package com.alex.asset.product.controller;


import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.dto.ProductV1Dto;
import com.alex.asset.product.dto.ProductV3Dto;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Get products(only active)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductV1Dto> getActiveProducts() {
        log.info(TAG + "Try to get all active products");
        return productService.getActive();
    }

    @Operation(summary = "Get all products (active and not)")
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductV1Dto> getAllProducts() {
        log.info(TAG + "Try to get all products");
        return productService.getAll();
    }


    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductV1Dto getById(
            @PathVariable("id") Long id) {
        log.info(TAG + "Try to get product by id {}", id);
        return productService.getById(id);
    }

    @Operation(summary = "Get short product by id (for android app")
    @GetMapping("/app/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductV3Dto getShortProductById(
            @PathVariable("id") Long id) {
        log.info(TAG + "Try to get product by id {}", id);
        return productService.getShortProductById(id);
    }


    @Operation(summary = "Add new product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductV1Dto addNewProduct(
            @RequestBody ProductV1Dto productV1Dto) {
        log.info(TAG + "get all products for company");
        return productService.create(
                productV1Dto,
                SecHolder.getUserId()
        );
    }

    @Operation(summary = "Update product by id and its new active status")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(
            @RequestBody Map<String, Object> updates) {
        log.info(TAG + "Update product");
        productService.updateProduct(
                SecHolder.getUserId(),
                updates
        );
    }


    @Operation(summary = "Update visibility fo product by id and its new active status")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public void updateVisibilityOfProduct(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Update product visibility");
        productService.updateVisibility(
                dtoActive,
                SecHolder.getUserId()
        );
    }


    @Operation(summary = "Get short products by title or bar code")
    @GetMapping("/filter/{key_word}")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, String> getProductsByTitleOrBarCode(
            @PathVariable("key_word") String keyWord) {
        log.info(TAG + "Try to get all products by key word {}", keyWord);
        return productService.getByTitleOrBarCode(keyWord);
    }


    @Operation(summary = "Get product history by id")
    @GetMapping("/history/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductHistoryDto> getHistoryOfProductById(
            @PathVariable("id") Long productId) {
        log.info(TAG + "Get history of product");
        return productService.getHistoryOfProductById(productId);
    }


}
