package com.alex.asset.core.controller;


import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.dto.ScrapDto;
import com.alex.asset.core.dto.simple.ActiveDto;
import com.alex.asset.core.service.ProductService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
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
@RequestMapping("/api/core/product")
public class ProductController {
    private final String TAG = "PRODUCT_CONTROLLER - ";

    private final ProductService productService;

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
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id) {
        log.info(TAG + "Try to get product by id {}", id);
        ProductDto productDTO = productService.getById(id);
        if (productDTO == null) {
            log.error(TAG + "Products with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody ProductDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "get all products for company");
        ProductDto productDTO = productService.create(dto, principal.getUserId());
        if (productDTO == null) {
            log.error(TAG + "Something wrong");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto dto) {
        log.info(TAG + "get all products for company");
        ProductDto productDto = productService.update(dto);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    public ResponseEntity<HttpStatus> updateVisibilityOfProduct(@RequestBody ActiveDto dto) {
        log.info(TAG + "Update product visibility with id {} and value {}", dto.getId(), dto.isActive());
        boolean result = productService.updateVisibility(dto);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        log.info(TAG + "Try to delete product with id {}", id);
        boolean result = productService.deleteById(id);
        if (!result) {
            log.error(TAG + "Products with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/filter/{title}")
    public ResponseEntity<Map<Long, String>> getProductsByTitle(@PathVariable("title") String title) {
        log.info(TAG + "Try to get all products by title {}", title);
        Map<Long, String> products = productService.getByTitle(title);
        if (products.isEmpty()) {
            log.warn(TAG + "Products with title {} not found", title);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/scrap")
    public ResponseEntity<HttpStatus> scrapProduct(@RequestBody ScrapDto dto) {
        log.info(TAG + "Scrap product with id {}", dto.getId());
        boolean result = productService.scraping(dto);
        if (!result) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
