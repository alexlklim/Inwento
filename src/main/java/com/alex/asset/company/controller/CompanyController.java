package com.alex.asset.company.controller;

import com.alex.asset.company.domain.CompanyDto;
import com.alex.asset.company.domain.DataDto;
import com.alex.asset.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
@Tag(name = "Company Controller", description = "Company API")
public class CompanyController {
    private final String TAG = "COMPANY_CONTROLLER - ";
    private final CompanyService companyService;


    @Operation(summary = "Get info about company")
    @GetMapping
    public ResponseEntity<CompanyDto> getInfo() {
        return new ResponseEntity<>(companyService.getInfoAboutCompany(), HttpStatus.OK);
    }

    @Operation(summary = "Update company info, only for ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<CompanyDto> updateCompany(@RequestBody CompanyDto dto) {
        log.info(TAG + "try to update company ");
        return new ResponseEntity<>(companyService.updateCompany(dto), HttpStatus.OK);
    }



    @GetMapping("/all")
    public ResponseEntity<DataDto> getAllFields() {
        log.info(TAG + "Try to get all fields");
        return new ResponseEntity<>(companyService.getAllFields(), HttpStatus.OK);
    }


}
