package com.alex.asset.core.controller;


import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.dto.ShortProduct;
import com.alex.asset.core.dto.Dto;
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
    public ResponseEntity<List<ProductDto>> getAllByCompany(Authentication authentication) {
        log.info(TAG + "get all products for company");
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        List<ProductDto> products = productService.getAllByCompany(principal.getCompanyId());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> add(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "add empty product {}", principal);
        return new ResponseEntity<>(
                productService.addEmptyForCompany(principal.getCompanyId(), principal.getUserId()),
                HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(
            @PathVariable("id") Long id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        ProductDto dto = productService.getProductByCompanyAndId(principal.getCompanyId(), id);
        if (dto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id, @RequestBody ProductDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        boolean result = productService.update(id, dto, principal);
        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> makeInactive(
            @PathVariable("id") Long id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = productService.makeInactive(id, principal);
        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = productService.delete(id, principal);
        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @GetMapping("/filter/title")
    public ResponseEntity<List<ShortProduct>> getProductsByTitle(
            @RequestBody Dto dto, Authentication authentication){
        System.out.println(authentication);

        return new ResponseEntity<>(productService.getProductsByTitle(dto.getName()), HttpStatus.OK);
    }

}
