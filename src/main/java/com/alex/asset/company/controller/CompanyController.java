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
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto getInfo() {
        return companyService.getInfoAboutCompany();
    }

    @Operation(summary = "Update company info, only for ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto updateCompany(
            @RequestBody CompanyDto companyDto) {
        log.info(TAG + "try to update company ");
        return companyService.updateCompany(companyDto, getUserId());
    }

    @Operation(summary = "Get all fields for product")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public DataDto getAllFields() {
        log.info(TAG + "Try to get all fields");
        return companyService.getAllFields();
    }


    @Operation(summary = "Get label configuration")
    @GetMapping("/label")
    @ResponseStatus(HttpStatus.OK)
    public LabelDto getLabelConfig() {
        log.info(TAG + "Get label configuration");
        return companyService.getLabelConfig();
    }

    @Operation(summary = "Update label configuration")
    @Secured("ROLE_ADMIN")
    @PutMapping("/label")
    @ResponseStatus(HttpStatus.OK)
    public void updateLabelConfig(
            @RequestBody LabelDto labelDto) {
        log.info(TAG + "Update label configuration");
        companyService.updateLabelConfig(getUserId(), labelDto);
    }


    @Operation(summary = "Get email configuration")
    @Secured("ROLE_ADMIN")
    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public EmailDto getEmailConfig() {
        log.info(TAG + "Get email configuration");
        return companyService.getEmailConfig();
    }


    @Operation(summary = "Update email configuration")
    @Secured("ROLE_ADMIN")
    @PutMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmailConfig(
            @RequestBody EmailDto emailDto) {
        log.info(TAG + "Update email configuration");
        companyService.updateEmailConfig(getUserId(), emailDto);
    }

}
