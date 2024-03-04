package com.alex.asset.core.controller;

import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.core.dto.EmpDto;
import com.alex.asset.core.dto.simple.ActiveDto;
import com.alex.asset.core.service.CompanyService;
import com.alex.asset.security.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j 
@RestController 
@CrossOrigin
@RequiredArgsConstructor 
@RequestMapping("/api/core/company")
public class CompanyController {
    private final String TAG = "COMPANY_CONTROLLER - ";
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<CompanyDto> getInfoAboutCompany() {
        return new ResponseEntity<>(companyService.getInfoAboutCompany(), HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<CompanyDto> updateCompany(@RequestBody CompanyDto dto) {
        log.info(TAG + "try to update company ");
        return new ResponseEntity<>(companyService.updateCompany(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpDto> getInfoAboutEmployee(@PathVariable("id") Long id) {
        return new ResponseEntity<>(companyService.getInfoAboutEmpById(id), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/emp")
    public ResponseEntity<List<UserDto>> getAllEmployee() {
        return new ResponseEntity<>(companyService.getAllEmployee(), HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @PutMapping("/emp")
    public ResponseEntity<HttpStatus> changeUserVisibility(@RequestBody ActiveDto dto) {
        log.info(TAG + "Try to change user visibility");
        if (!companyService.changeUserVisibility(dto)) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
