package com.alex.asset.product.controller;


import com.alex.asset.product.dto.ProductDto;
import com.alex.asset.product.dto.ScrapDto;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
        List<ProductDto> productDTOs = productService.getActive();
        if (productDTOs.isEmpty()) {
            log.warn(TAG + "Where are not products to show");
            return new ResponseEntity<>(productDTOs, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get all products (active and not)")
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info(TAG + "Try to get all products");
        List<ProductDto> productDTOs = productService.getAll();
        if (productDTOs.isEmpty()) {
            log.warn(TAG + "Where are not products to show");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }


    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(
            @PathVariable("id") Long id) {
        log.info(TAG + "Try to get product by id {}", id);
        ProductDto productDTO = productService.getById(id);
        if (productDTO == null) {
            log.error(TAG + "Products with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @Operation(summary = "Add new product")
    @PostMapping
    public ResponseEntity<ProductDto> addNewProduct(
            @RequestBody ProductDto dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "get all products for company");
        ProductDto productDTO = productService.create(dto, principal.getUserId());
        if (productDTO == null) {
            log.error(TAG + "Something wrong");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update Product")
    @PutMapping
    public ResponseEntity<HttpStatus> updateProduct(
            @RequestBody ProductDto dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "get all products for company");
        productService.update(dto, principal.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update visibility fo product by id and its new active status")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    public ResponseEntity<HttpStatus> updateVisibilityOfProduct(
            @RequestBody DtoActive dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Update product visibility with id {} and value {}", dto.getId(), dto.isActive());
        boolean result = productService.updateVisibility(dto, principal.getUserId());
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Get short products by title")
    @GetMapping("/filter/{title}")
    public ResponseEntity<Map<Long, String>> getProductsByTitle(
            @PathVariable("title") String title) {
        log.info(TAG + "Try to get all products by title {}", title);
        Map<Long, String> products = productService.getByTitle(title);
        if (products.isEmpty()) {
            log.warn(TAG + "Products with title {} not found", title);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Scrap product (use isScrap to make product scrap or not)")
    @PutMapping("/scrap")
    public ResponseEntity<HttpStatus> scrapProduct(
            @RequestBody ScrapDto dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Scrap product with id {}", dto.getId());
        boolean result = productService.scraping(dto, principal.getUserId());
        if (!result) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
