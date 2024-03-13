package com.alex.asset.configure.controllers;

import com.alex.asset.company.domain.DataDto;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/company/type")
@Tag(name = "Type Controller", description = "Type API")
public class TypeController {

    private final String TAG = "TYPE_CONTROLLER - ";

    private final TypeService typeService;


    @Operation(summary = "Get all types and subtypes")
    @GetMapping("/all")
    public ResponseEntity<List<DataDto.Type>> getAllTypesAndSubtypes() {
        log.info(TAG + "Try to get all types and subtypes");
        typeService.getTypes();
        return new ResponseEntity<>(
                typeService.getTypes(),
                HttpStatus.OK);
    }

    @Operation(summary = "Add new Types")
    @PostMapping
    public ResponseEntity<HttpStatus> addTypes(
            @RequestBody List<String> listTypes,
            Authentication authentication) {
        log.info(TAG + "Try to add types");
        typeService.addTypes(
                listTypes,
                ((CustomPrincipal) authentication.getPrincipal()).getUserId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Change visibility of type by id")
    @PutMapping
    public ResponseEntity<HttpStatus> changeVisibilityOfType(
            @RequestBody DtoActive dtoActive,
            Authentication authentication) {
        log.info(TAG + "Try to change visibility of type");
        typeService.changeVisibilityOfType(
                dtoActive,
                ((CustomPrincipal) authentication.getPrincipal()).getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Add new Subtype to special type")
    @PostMapping("/{id}/subtype")
    public ResponseEntity<HttpStatus> addSubtypes(
            @PathVariable("id") Long typeId,
            @RequestBody List<String> listSubtype,
            Authentication authentication) {
        log.info(TAG + "Try to add subtypes");
        typeService.addSubtypes(
                typeId,
                listSubtype,
                ((CustomPrincipal) authentication.getPrincipal()).getUserId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Change visibility of subtype by id")
    @PutMapping("/subtype")
    public ResponseEntity<HttpStatus> changeVisibilityOfSubtype(
            @RequestBody DtoActive dtoActive,
            Authentication authentication) {
        log.info(TAG + "Try to change visibility of subtype");
        typeService.changeVisibilityOfSubtype(
                dtoActive,
                ((CustomPrincipal) authentication.getPrincipal()).getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
