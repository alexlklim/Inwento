package com.alex.asset.core.controller.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company/type")
public class TypeController {


    @PostMapping
    public ResponseEntity<HttpStatus> addTypes(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{type}")
    public ResponseEntity<HttpStatus> deleteType(
            @PathVariable("type") String type, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/subtype")
    public ResponseEntity<HttpStatus> addSubtypes(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/subtype/{subtype}")
    public ResponseEntity<HttpStatus> deleteSubtype(
            @PathVariable("subtype") String type, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
