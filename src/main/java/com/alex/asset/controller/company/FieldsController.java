package com.alex.asset.controller.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class FieldsController {


    @PostMapping("/branch")
    public ResponseEntity<?> addBranch(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/mpk")
    public ResponseEntity<?> addMPK(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/producer")
    public ResponseEntity<?> addProducer(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/supplier")
    public ResponseEntity<?> addSupplier(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }






    @DeleteMapping("/branch/{name}")
    public ResponseEntity<?> deleteBranch(
            @PathVariable("name") String branchName, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/mpk/{name}")
    public ResponseEntity<?> deleteMPK(
            @PathVariable("name") String mpkName, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/producer/{name}")
    public ResponseEntity<?> deleteProducer(
            @PathVariable("name") String producerName, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/supplier/{name}")
    public ResponseEntity<?> deleteSupplier(
            @PathVariable("name") String supplierName, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }












}
