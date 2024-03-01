package com.alex.asset.core.controller.company;

import com.alex.asset.core.dto.simple.ActiveDto;
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
    @PutMapping("/type")
    public ResponseEntity<HttpStatus> changeVisibilityOfType(@RequestBody ActiveDto dto) {
        boolean result = typeService.changeVisibilityOfType(dto);
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @PostMapping("/{id}/subtype")
    public ResponseEntity<HttpStatus> addSubtypes(@PathVariable("id") Long id, @RequestBody List<String> list) {
        boolean result = typeService.addSubtypes(id, list);
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/subtype")
    public ResponseEntity<HttpStatus> changeVisibilityOfSubtype(@RequestBody ActiveDto dto) {
        boolean result = typeService.changeVisibilityOfSubtype(dto);
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);    }

}
