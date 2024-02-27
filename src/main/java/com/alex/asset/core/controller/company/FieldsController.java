package com.alex.asset.core.controller.company;

import com.alex.asset.core.dto.Dto;
import com.alex.asset.core.service.CompanyService;
import com.alex.asset.core.service.FieldService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company/fields")
public class FieldsController {
    private final String TAG = "FIELDS_CONTROLLER - ";

    private final CompanyService companyService;

    private final FieldService fieldService;

    @GetMapping
    public ResponseEntity<FieldsDto> getAllFields(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Try to get all fields");

        return new ResponseEntity<>(companyService.getAllFields(principal), HttpStatus.OK);
    }

    @PostMapping("/asset-status")
    public ResponseEntity<HttpStatus> updateAssetStatuses(@RequestBody List<String> list, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Try to update asset statuses");
        boolean result = fieldService.updateAssetStatuses(list, principal.getCompanyId());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/kst")
    public ResponseEntity<HttpStatus> updateKst(@RequestBody List<String> list, Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Try to update kst");
        boolean result = fieldService.updateKst(list, principal.getCompanyId());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/units")
    public ResponseEntity<HttpStatus> updateUnits(@RequestBody List<String> list, Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Try to update units");
        boolean result = fieldService.updateUnits(list, principal.getCompanyId());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }






    @PostMapping("/branch")
    public ResponseEntity<HttpStatus> addBranches(
            @RequestBody List<String> list, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Try to add branch");
        boolean result = fieldService.addBranches (list, principal.getCompanyId());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/mpk")
    public ResponseEntity<HttpStatus> addMPKs(
            @RequestBody List<String> list, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Try to add MPK");
        boolean result = fieldService.addMPKs(list, principal.getCompanyId());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }







    @DeleteMapping("/branch")
    public ResponseEntity<HttpStatus> deleteBranch(
            @RequestBody Dto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Try to delete branch");
        boolean result = fieldService.deleteBranch(dto, principal.getCompanyId());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/mpk")
    public ResponseEntity<HttpStatus> deleteMPK(
            @RequestBody Dto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Try to delete MPK");
        boolean result = fieldService.deleteMPK(dto, principal.getCompanyId());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
