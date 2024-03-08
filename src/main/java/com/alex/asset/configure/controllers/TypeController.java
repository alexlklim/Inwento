package com.alex.asset.configure.controllers;

import com.alex.asset.configure.services.TypeService;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company/type")
@Tag(name = "Type Controller", description = "Type API")
public class TypeController {

    private final String TAG = "TYPE_CONTROLLER - ";

    private final TypeService typeService;

    @Operation(summary = "Add new Types")
    @PostMapping
    public ResponseEntity<HttpStatus> addTypes(@RequestBody List<String> list) {
        log.info(TAG + "Try to add types {}", list.stream().toList());
        typeService.addTypes(list);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Change visibility of type by id")
    @PutMapping
    public ResponseEntity<HttpStatus> changeVisibilityOfType(@RequestBody DtoActive dto) {
        log.info(TAG + "Try to change visibility of type with id {} to status {}", dto.getId(), dto.isActive());
        boolean result = typeService.changeVisibilityOfType(dto);
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Add new Subtype to special type")
    @PostMapping("/{id}/subtype")
    public ResponseEntity<HttpStatus> addSubtypes(@PathVariable("id") Long id, @RequestBody List<String> list) {
        log.info(TAG + "Try to add subtypes {} to type with id {}", list.stream().toList(), id);
        boolean result = typeService.addSubtypes(id, list);
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Change visibility of subtype by id")
    @PutMapping("/subtype")
    public ResponseEntity<HttpStatus> changeVisibilityOfSubtype(@RequestBody DtoActive dto) {
        log.info(TAG + "Try to change visibility of subtype with id {} to status {}", dto.getId(), dto.isActive());
        boolean result = typeService.changeVisibilityOfSubtype(dto);
        if (!result) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
