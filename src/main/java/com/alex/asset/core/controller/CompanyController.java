package com.alex.asset.core.controller;

import com.alex.asset.core.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {



    @GetMapping
    public ResponseEntity<CompanyDto> info(Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody CompanyDto dto, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody CompanyDto dto, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
