package com.alex.asset.core.controller;


import com.alex.asset.core.dto.LongDto;
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
        List<ProductDto> products = productService.getAllProductsForCompany(principal.getCompanyId());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LongDto> add(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "add empty product {}", principal);

        LongDto dto = new LongDto();
        dto.setName(productService.addEmptyProductForCompany(principal.getCompanyId(), principal.getUserId()));
        return new ResponseEntity<>(
                dto,
                HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(
            @PathVariable("id") Long id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        ProductDto dto = productService.getProductByIdForCompany(principal.getCompanyId(), id);
        if (dto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id, @RequestBody ProductDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        boolean result = productService.updateProductByIdForCompany(principal.getCompanyId(), id, dto);
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> makeInactive(
            @PathVariable("id") Long productId, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = productService.makeProductInvisibleByIdForCompany(principal.getCompanyId(), productId);
        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long productId, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = productService.deleteProductByIdForCompany(principal.getCompanyId(), productId);
        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @GetMapping("/filter/title")
    public ResponseEntity<List<ShortProduct>> getProductsByTitle(
            @RequestBody Dto dto, Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return new ResponseEntity<>(
                productService.getProductsByTitleForCompany(principal.getCompanyId(), dto.getName()),
                HttpStatus.OK);
    }

}
