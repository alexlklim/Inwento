package com.alex.asset.inventory.controller;

import com.alex.asset.inventory.dto.InventoryDto;
import com.alex.asset.inventory.service.InventoryService;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/invent")
@Tag(name = "Inventory Controller", description = "Inventory API")
public class InventoryController {
    private final String TAG = "INVENTORY_CONTROLLER - ";

    private final InventoryService inventoryService;


    @Operation(summary = "Get inventory by id")
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDto getInventoryById(
            @PathVariable("id") Long inventoryId) {
        log.info(TAG + "Get inventory with id {}", inventoryId);
        return inventoryService.getInventoryById(inventoryId);
    }


    @Operation(summary = "Check is inventory now or not " +
            "(it will be used in android app (main page) to notify users that inventory is started")
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInventoryActive() {
        log.info(TAG + "Check is inventory taking place now or not");
        return inventoryService.isInventoryNow();
    }

    @Operation(summary = "get current active inventory (it returns 404 if no active inventory now")
    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDto getCurrentInventory() {
        log.info(TAG + "get current inventory");
        return inventoryService.getCurrentInventory();
    }


    @Operation(summary = "Start new inventarization")
    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @RequestBody InventoryDto inventoryDto) {
        log.info(TAG + "Create inventory");
        inventoryService.startInventory(
                SecHolder.getUserId(),
                inventoryDto);
    }

    @Operation(summary = "Update inventarization")
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @PathVariable("id") Long inventoryId,
            @RequestBody InventoryDto inventoryDto) {
        log.info(TAG + "Update inventory");
        inventoryService.updateInventory(
                SecHolder.getUserId(),
                inventoryId,
                inventoryDto);
    }

    @Operation(summary = "Change visibility of inventory")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public void changeVisibility(
            @RequestBody DtoActive activeDto) {
        log.info(TAG + "Finish inventory");
        inventoryService.changeVisibility(
                SecHolder.getUserId(),
                activeDto);
    }


}
