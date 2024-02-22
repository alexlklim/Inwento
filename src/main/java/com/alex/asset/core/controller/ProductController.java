package com.alex.asset.core.controller;


import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.service.ProductService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company/product")
public class ProductController {
    private final String TAG = "PRODUCT_CONTROLLER - ";

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "get all products for company {}", principal.getComapnyUUID());
        List<ProductDto> products = productService.getAll(principal.getComapnyUUID());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> add(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "add empty product for company {}", principal.getComapnyUUID());
        Long id = productService.add(principal);
        if (id == null)return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(
            @PathVariable("id") Long id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "get productby id {} for company {}", id, principal.getComapnyUUID());
        ProductDto dto = productService.getProductById(id, principal);
        if (dto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id, @RequestBody ProductDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "update product with id: {} for company {}", id, principal.getComapnyUUID());

        boolean result = productService.update(id, dto, principal);
        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> makeInactive(
            @PathVariable("id") Long id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "delete product with id: {} for company {}", id, principal.getComapnyUUID());
        boolean result = productService.makeInactive(id, principal);
        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "delete product with id: {} for company {}", id, principal.getComapnyUUID());
        boolean result = productService.delete(id, principal);
        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
