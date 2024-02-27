package com.alex.asset.core.controller;


import com.alex.asset.core.domain.Product;
import com.alex.asset.core.dto.simple.DtoLong;
import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.dto.simple.DtoName;
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
@RequestMapping("/api/core/product")
public class ProductController {
    private final String TAG = "PRODUCT_CONTROLLER - ";

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllByCompany() {
        log.info(TAG + "get all products for company");
        List<Product> products = productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }




//    @PostMapping
//    public ResponseEntity<DtoLong> add(Authentication authentication) {
//        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
//        log.info(TAG + "add empty product {}", principal);
//
//        DtoLong dto = new DtoLong();
//        dto.setId(productService.addEmptyProductForCompany(principal.getCompanyId(), principal.getUserId()));
//        return new ResponseEntity<>(
//                dto,
//                HttpStatus.OK);
//    }
//
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductDto> getById(
//            @PathVariable("id") Long id, Authentication authentication) {
//        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
//
//        ProductDto dto = productService.getProductByIdForCompany(principal.getCompanyId(), id);
//        if (dto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(dto, HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<HttpStatus> update(
//            @PathVariable("id") Long id, @RequestBody ProductDto dto, Authentication authentication) {
//        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
//
//        boolean result = productService.updateProductByIdForCompany(principal.getCompanyId(), id, dto);
//        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> makeInactive(
//            @PathVariable("id") Long productId, Authentication authentication) {
//        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
//        boolean result = productService.makeProductInvisibleByIdForCompany(principal.getCompanyId(), productId);
//        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/del/{id}")
//    public ResponseEntity<HttpStatus> delete(
//            @PathVariable("id") Long productId, Authentication authentication) {
//        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
//        boolean result = productService.deleteProductByIdForCompany(principal.getCompanyId(), productId);
//        if (!result)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//
//
//
//    @GetMapping("/filter/title")
//    public ResponseEntity<List<ShortProduct>> getProductsByTitle(
//            @RequestBody DtoName dto, Authentication authentication){
//        log.info(TAG + " ");
//        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
//
//        return new ResponseEntity<>(
//                productService.getProductsByTitleForCompany(principal.getCompanyId(), dto.getName()),
//                HttpStatus.OK);
//    }

}
