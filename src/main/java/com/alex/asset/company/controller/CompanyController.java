package com.alex.asset.company.controller;

import com.alex.asset.company.domain.CompanyDto;
import com.alex.asset.company.domain.DataDto;
import com.alex.asset.company.domain.EmailDto;
import com.alex.asset.company.domain.LabelDto;
import com.alex.asset.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.alex.asset.utils.SecHolder.getUserId;

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
        return new ResponseEntity<>(
                companyService.getInfoAboutCompany(),
                HttpStatus.OK);
    }

    @Operation(summary = "Update company info, only for ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<CompanyDto> updateCompany(
            @RequestBody CompanyDto companyDto) {
        log.info(TAG + "try to update company ");
        return new ResponseEntity<>(
                companyService.updateCompany(companyDto, getUserId()),
                HttpStatus.OK);
    }

    @Operation(summary = "Get all fields for product")
    @GetMapping("/all")
    public ResponseEntity<DataDto> getAllFields() {
        log.info(TAG + "Try to get all fields");
        return new ResponseEntity<>(
                companyService.getAllFields(),
                HttpStatus.OK);
    }


    @Operation(summary = "Get label configuration")
    @GetMapping("/label")
    public ResponseEntity<LabelDto> getLabelConfig() {
        log.info(TAG + "Get label configuration");
        return new ResponseEntity<>(
                companyService.getLabelConfig(),
                HttpStatus.OK);
    }

    @Operation(summary = "Update label configuration")
    @Secured("ROLE_ADMIN")
    @PutMapping("/label")
    public ResponseEntity<HttpStatus> updateLabelConfig(
            @RequestBody LabelDto labelDto) {
        log.info(TAG + "Update label configuration");
        companyService.updateLabelConfig(getUserId(), labelDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Get email configuration")
    @Secured("ROLE_ADMIN")
    @GetMapping("/email")
    public ResponseEntity<EmailDto> getEmailConfig() {
        log.info(TAG + "Get email configuration");
        return new ResponseEntity<>(
                companyService.getEmailConfig(),
                HttpStatus.OK);
    }


    @Operation(summary = "Update email configuration")
    @Secured("ROLE_ADMIN")
    @PutMapping("/email")
    public ResponseEntity<HttpStatus> updateEmailConfig(
            @RequestBody EmailDto emailDto) {
        log.info(TAG + "Update email configuration");
        companyService.updateEmailConfig(getUserId(), emailDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
