package com.alex.asset.controller;

import com.alex.asset.dto.CompanyDto;
import com.alex.asset.dto.FieldsDto;
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


    @GetMapping("/info")
    public ResponseEntity<CompanyDto> info(Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(
            @RequestBody CompanyDto dto, Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/fields")
    public ResponseEntity<FieldsDto> getFields(Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/secret")
    public ResponseEntity<?> changeSecret(
            @RequestBody String oldSecret, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/company/{secret}")
    public ResponseEntity<?> delete(
            @PathVariable("secret") String secret, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
