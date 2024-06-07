package com.alex.asset.configure.controllers;


import com.alex.asset.configure.domain.KST;
import com.alex.asset.configure.services.ConfiguratorService;
import com.alex.asset.exceptions.company.KSTNumIsToShortException;
import com.alex.asset.utils.SecHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/company/configurator")
@Tag(name = "Configurator Controller", description = "Configure API")
public class ConfigureController {
    private final String TAG = "CONFIGURE_CONTROLLER - ";

    private final ConfiguratorService configureService;


    @Operation(summary = "get all configurations (full view, active and not active)")
    @GetMapping("/all")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getConfigurations(
            @RequestParam(required = false) List<String> fields) {
        log.info(TAG + "getConfigurations: " + fields);
        return configureService.getAllConfigurations(fields, false);
    }

    @Operation(summary = "get all configurations (full view, active and not active)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getOnlyActiveConfigurations(
            @RequestParam(required = false) List<String> fields) {
        log.info(TAG + "getOnlyActiveConfigurations: " + fields);
        return configureService.getAllConfigurations(fields, true);
    }


    @Operation(summary = "Update configurations")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateConfigurations(
            @RequestBody Map<String, Object> updates) {
        log.info(TAG + "updateConfigurations");
        configureService.updateConfigurations(updates, SecHolder.getUserId());
    }



    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all active KST by KST num")
    @GetMapping("/kst/filter/num/{kst_num}")
    public List<KST> getKSTByNum(
            @PathVariable("kst_num") String num) {
        log.info(TAG + "getKSTByNum");
        if (num.length() < 2)
             throw new KSTNumIsToShortException("Please, provide more longer KST num (length >= 2)");
        return configureService.getKSTsByNum(num);
    }


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all active KST by KST name")
    @GetMapping("/kst/filter/kst/{kst}")
    public List<KST> getKSTByKST(
            @PathVariable("kst") String kst) {
        log.info(TAG + "getKSTByNum");
        if (kst.length() < 3)
            throw new KSTNumIsToShortException("Please, provide more longer KST name (length >= 3)");
        return configureService.getKSTsByKST(kst);
    }

}
