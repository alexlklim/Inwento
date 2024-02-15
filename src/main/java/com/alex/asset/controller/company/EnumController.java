package com.alex.asset.controller.company;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class EnumController {



    @GetMapping("/kst")
    public ResponseEntity<List<String>> getKST(Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<String>> getStatus(Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/units")
    public ResponseEntity<List<String>> getUnits(Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }






    @PostMapping("/kst")
    public ResponseEntity<?> addKST(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<?> addStatus(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/units")
    public ResponseEntity<?> addUnits(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }





    @DeleteMapping("/kst/{name}")
    public ResponseEntity<?> deleteKST(
            @PathVariable("name") String branchName, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/status/{name}")
    public ResponseEntity<?> deleteStatus(
            @PathVariable("name") String branchName, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/units/{name}")
    public ResponseEntity<?> deleteUnits(
            @PathVariable("name") String branchName, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
