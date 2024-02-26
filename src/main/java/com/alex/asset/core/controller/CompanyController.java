package com.alex.asset.core.controller;

import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.core.service.CompanyService;
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
    public ResponseEntity<CompanyDto> getInfoAboutCompany(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "try to get info about company {}", principal.getCompanyId());
        System.out.println(principal);
        return new ResponseEntity<>(
                companyService.getInfoAboutCompany(principal.getCompanyId(), principal.getUserId()),
                HttpStatus.OK);
    }


    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<HttpStatus> createNewCompany(@RequestBody CompanyDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "try to create company {} by {}", dto.getCompany(), principal.getName());
        if (principal.getCompanyId() != null) {
            log.warn("User: {} already belong to company: {}", principal.getName(), principal.getCompanyId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        CompanyDto companyDto = companyService.addCompanyForUser(principal.getUserId(), dto);
        log.error(TAG + "Company {} was added", companyDto.getCompany());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<HttpStatus> update(
            @RequestBody CompanyDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "try to update company {} by {}", dto.getCompany(), principal.getName());

        CompanyDto companyDto = companyService.update(dto, principal);
        if (companyDto == null) {
            log.warn(TAG + "User: {} isn't owner of any company:", principal.getName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.error(TAG + "Company: '{}' was updated by its owner {}", companyDto.getCompany(), principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<HttpStatus> makeInactive(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "try to make inactive company {} by {}", principal.getCompanyId(), principal.getName());

        if (!companyService.makeInactive(principal)) {
            log.warn(TAG + "User: {} isn't owner of any company:", principal.getName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.error(TAG + "Company {} was inactive with owner {}", principal.getCompanyId(), principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
