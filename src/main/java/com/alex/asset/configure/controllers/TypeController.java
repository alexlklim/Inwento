package com.alex.asset.configure.controllers;

import com.alex.asset.company.domain.DataDto;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<DataDto.Type> getAllTypesAndSubtypes() {
        log.info(TAG + "Try to get all types and subtypes");
        typeService.getTypes();
        return typeService.getTypes();
    }

    @Operation(summary = "Add new Types")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTypes(
            @RequestBody List<String> listTypes) {
        log.info(TAG + "Try to add types");
        typeService.addTypes(
                listTypes,
                SecHolder.getUserId());
    }

    @Operation(summary = "Change visibility of type by id")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void changeVisibilityOfType(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Try to change visibility of type");
        typeService.changeVisibilityOfType(
                dtoActive,
                SecHolder.getUserId());
    }


    @Operation(summary = "Add new Subtype to special type")
    @PostMapping("/{type_id}/subtype")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubtypes(
            @PathVariable("type_id") Long typeId,
            @RequestBody List<String> listSubtype) {
        log.info(TAG + "Try to add subtypes");
        typeService.addSubtypes(
                typeId,
                listSubtype,
                SecHolder.getUserId());
    }


    @Operation(summary = "Change visibility of subtype by id")
    @PutMapping("/subtype")
    @ResponseStatus(HttpStatus.OK)
    public void changeVisibilityOfSubtype(
            @RequestBody DtoActive dtoActive) {
        log.info(TAG + "Try to change visibility of subtype");
        typeService.changeVisibilityOfSubtype(
                dtoActive,
                SecHolder.getUserId());
    }


}
