package com.alex.asset.core.controller.company;

import com.alex.asset.core.dto.FieldsDto;
import com.alex.asset.core.dto.SimpleDto;
import com.alex.asset.core.service.impl.CompanyService;
import com.alex.asset.core.service.impl.FieldService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company/fields")
public class FieldsController {

    private final CompanyService companyService;

    private final FieldService fieldService;

    @GetMapping
    public ResponseEntity<FieldsDto> getAllFields(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        if (principal.getComapnyUUID() == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(companyService.getAllFields(principal.getComapnyUUID()), HttpStatus.OK);
    }

    @PostMapping("/asset-status")
    public ResponseEntity<HttpStatus> updateAssetStatuses(@RequestBody List<String> list, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.updateAssetStatuses(list, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/kst")
    public ResponseEntity<HttpStatus> updateKst(@RequestBody List<String> list, Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.updateKst(list, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/units")
    public ResponseEntity<HttpStatus> updateUnits(@RequestBody List<String> list, Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.updateUnits(list, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }






    @PostMapping("/branch")
    public ResponseEntity<HttpStatus> addBranches(
            @RequestBody List<String> list, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.addBranches (list, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/mpk")
    public ResponseEntity<HttpStatus> addMPKs(
            @RequestBody List<String> list, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.addMPKs(list, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/producer")
    public ResponseEntity<HttpStatus> addProducers(
            @RequestBody List<String> list, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.addProducers(list, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/supplier")
    public ResponseEntity<HttpStatus> addSuppliers(
            @RequestBody List<String> list, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.addSuppliers(list, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }






    @DeleteMapping("/branch")
    public ResponseEntity<HttpStatus> deleteBranch(
            @RequestBody SimpleDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.deleteBranch(dto, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/mpk")
    public ResponseEntity<HttpStatus> deleteMPK(
            @RequestBody SimpleDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.deleteMPK(dto, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/producer")
    public ResponseEntity<HttpStatus> deleteProducer(
            @RequestBody SimpleDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.deleteProducer(dto, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/supplier")
    public ResponseEntity<HttpStatus> deleteSupplier(
            @RequestBody SimpleDto dto, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        boolean result = fieldService.deleteSupplier(dto, principal.getComapnyUUID());
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
