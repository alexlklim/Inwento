package com.alex.asset.controller;


import com.alex.asset.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/company/product")
public class ProductController {

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody ProductDto dto, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductDto>> getById(
            @PathVariable("id") Long id, Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id, @RequestBody ProductDto dto, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
