package com.alex.asset.core.controller.company;

import com.alex.asset.core.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company")
public class TypeController {

    private final TypeService typeService;

    @PostMapping("/type")
    public ResponseEntity<HttpStatus> addTypes(@RequestBody List<String> list) {
        typeService.addTypes(list);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/{id}/subtype")
    public ResponseEntity<HttpStatus> addSubtypes(@PathVariable("id") Long id, @RequestBody List<String> list) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/subtype/{id}")
    public ResponseEntity<HttpStatus> deleteSubtype(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
