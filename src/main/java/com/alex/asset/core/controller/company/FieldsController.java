package com.alex.asset.core.controller.company;

import com.alex.asset.core.dto.FieldsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/company/fields")
public class FieldsController {

    @GetMapping
    public ResponseEntity<FieldsDto> getAllFields(Authentication authentication) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/asset-status")
    public ResponseEntity<HttpStatus> updateAssetStatuses(List<String> list){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/kst")
    public ResponseEntity<HttpStatus> updateKST(List<String> list){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/units")
    public ResponseEntity<HttpStatus> updateUnits(List<String> list){
        return new ResponseEntity<>(HttpStatus.OK);
    }






    @PostMapping("/branch")
    public ResponseEntity<HttpStatus> addBranches(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/mpk")
    public ResponseEntity<HttpStatus> addMPKs(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/producer")
    public ResponseEntity<HttpStatus> addProducers(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/supplier")
    public ResponseEntity<HttpStatus> addSuppliers(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }






    @DeleteMapping("/branch/{name}")
    public ResponseEntity<HttpStatus> deleteBranch(
            @PathVariable("name") String title, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/mpk/{name}")
    public ResponseEntity<HttpStatus> deleteMPK(
            @PathVariable("name") String title, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/producer/{name}")
    public ResponseEntity<HttpStatus> deleteProducer(
            @PathVariable("name") String title, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/supplier/{name}")
    public ResponseEntity<HttpStatus> deleteSupplier(
            @PathVariable("name") String title, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
