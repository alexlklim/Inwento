package com.alex.asset.core.controller;

import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.core.service.impl.CompanyService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company")
public class CompanyController {
    private final String TAG = "COMPANY_CONTROLLER - ";

    private final CompanyService companyService;



    @GetMapping
    public ResponseEntity<CompanyDto> info(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        if (principal.getComapnyUUID() == null){
            log.error(TAG + "User: {} doesn't belong to any company", principal.getUserUUID());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CompanyDto dto = companyService.getInfo(principal);
        if (dto == null) {
            log.error(TAG + "Get info for company: {} failed", principal.getComapnyUUID());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody CompanyDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        if (principal.getComapnyUUID() != null) {
            log.warn("User: {} already belong to company: {}", principal.getEmail(), principal.getComapnyUUID());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        CompanyDto companyDto = companyService.add(dto, principal);
        log.error(TAG + "Company {} was added", companyDto.getCompany());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<HttpStatus> update(
            @RequestBody CompanyDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        if (principal.getComapnyUUID() == null) {
            log.warn("User: {} doesnt belong to any company", principal.getEmail());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CompanyDto companyDto = companyService.update(dto, principal);
        if (companyDto == null) {
            log.warn(TAG + "User: {} isn't owner of any company:", principal.getEmail());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.error(TAG + "Company: '{}' was updated by its owner {}", companyDto.getCompany(), principal.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Secured({"ROLE_CLIENT"})
    @DeleteMapping
    public ResponseEntity<HttpStatus> makeInactive(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        if (principal.getComapnyUUID() == null) {
            log.warn("User: {} doesnt belong to any company", principal.getEmail());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!companyService.makeInactive(principal)) {
            log.warn(TAG + "User: {} isn't owner of any company:", principal.getEmail());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.error(TAG + "Company {} was inactive with owner {}", principal.getComapnyUUID(), principal.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
