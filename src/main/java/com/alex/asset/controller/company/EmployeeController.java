package com.alex.asset.controller.company;

import com.alex.asset.dto.CompanyDto;
import com.alex.asset.dto.EmployeeDto;
import com.alex.asset.dto.FieldsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/company/employee")
public class EmployeeController {


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> info(Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/{secret}")
    public ResponseEntity<?> delete(
            @PathVariable("secret") String secret, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}}")
    public ResponseEntity<?> getFields(
            @PathVariable("uuid") UUID secret, Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
