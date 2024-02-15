package com.alex.asset.controller.company;

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
public class TypeController {


    @PostMapping("/type")
    public ResponseEntity<?> addType(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{type}/subtype")
    public ResponseEntity<?> addSubtype(
            @RequestBody List<String> list, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
