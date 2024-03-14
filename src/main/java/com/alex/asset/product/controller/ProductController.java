package com.alex.asset.product.controller;


import com.alex.asset.product.dto.ProductDto;
import com.alex.asset.product.dto.ScrapDto;
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
                HttpStatus.OK);
    }

    @Operation(summary = "Get all products (active and not)")
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info(TAG + "Try to get all products");
        return new ResponseEntity<>(
                productService.getAll(),
                HttpStatus.OK);
    }


    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(
            @PathVariable("id") Long id) {
        log.info(TAG + "Try to get product by id {}", id);
        return new ResponseEntity<>(
                productService.getById(id),
                HttpStatus.OK);
    }

    @Operation(summary = "Add new product")
    @PostMapping
    public ResponseEntity<HttpStatus> addNewProduct(
            @RequestBody ProductDto productDto) {
        log.info(TAG + "get all products for company");
        productService.create(
                productDto,
                SecHolder.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update Product")
    @PutMapping
    public ResponseEntity<HttpStatus> updateProduct(
            @RequestBody ProductDto productDto) {
        log.info(TAG + "get all products for company");
        productService.update(
                productDto,
                SecHolder.getUserId());
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
                SecHolder.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Get short products by title")
    @GetMapping("/filter/{title}")
    public ResponseEntity<Map<Long, String>> getProductsByTitle(
            @PathVariable("title") String title) {
        log.info(TAG + "Try to get all products by title {}", title);
        return new ResponseEntity<>(
                productService.getByTitle(title),
                HttpStatus.OK);
    }

    @Operation(summary = "Scrap product (use isScrap to make product scrap or not)")
    @PutMapping("/scrap")
    public ResponseEntity<HttpStatus> scrapProduct(
            @RequestBody ScrapDto scrapDto) {
        log.info(TAG + "Scrap product");
        productService.scraping(
                scrapDto,
                SecHolder.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
