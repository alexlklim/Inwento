package com.alex.asset.inventory.controller;


import com.alex.asset.inventory.dto.InventoryDto;
import com.alex.asset.inventory.service.InventoryService;
import com.alex.asset.utils.SecHolder;
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
@RequestMapping("/api/v1/inventory/2/")
@Tag(name = "Inventory Controller", description = "Inventory API 2")
public class InvController {

    private final String TAG = "INVENTORY_CONTROLLER - ";

    private final InventoryService inventoryService;


    // get list of inventories with custom fields
    // get inventory by id with custom fields


    // create new inventory
    // update inventory

    @Operation(summary = "Get inventory by id")
    @GetMapping("/{inventory_id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDto getInventoryById(
            @PathVariable("inventory_id") Long inventoryId) {
        log.info(TAG + "Get inventory with id {}", inventoryId);
        return inventoryService.getInventoryById(inventoryId, SecHolder.getUserId());
    }

}
