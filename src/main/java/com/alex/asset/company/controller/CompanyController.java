package com.alex.asset.company.controller;

import com.alex.asset.company.domain.DataDto;
import com.alex.asset.company.service.CompanyService;
import com.alex.asset.utils.SecHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
@Tag(name = "Company Controller", description = "Company API")
public class CompanyController {
    private final String TAG = "COMPANY_CONTROLLER - ";
    private final CompanyService companyService;


    @Operation(summary = "Get info about company, send list of fields which you want to get/ " +
            "send nothing if you want to get all info about company and all configuration (branch/subtype/type/mpk")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getInfo(
            @RequestParam(required = false) List<String> fields) {
        log.info(TAG + "Get company info");

        return companyService.getInfoAboutCompany(fields);
    }


    @Operation(summary = "Update company info, only for ADMIN")
    @Secured("ROLE_ADMIN")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> updateCompany(
            @RequestBody Map<String, Object> updates) {
        log.info(TAG + "Update company info");
        return companyService.updateCompany(updates, SecHolder.getUserId());
    }


    @Operation(summary = "Get branches/locations/employees (for android app)")
    @GetMapping("/data")
    @ResponseStatus(HttpStatus.OK)
    public DataDto getDataForAndroidApp() {
        log.info(TAG + "Get data (branches/locations/employees)");
        return companyService.getData();
    }


}
