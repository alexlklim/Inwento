package com.alex.asset.inventory.controller;


import com.alex.asset.inventory.dto.InventoryDTO;
import com.alex.asset.inventory.service.InventoryService;
import com.alex.asset.utils.SecHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v2/inventory/")
@Tag(name = "Inventory Controller", description = "Inventory API")
public class InventoryController {
    private final String TAG = "INVENTORY_CONTROLLER - ";
    private final InventoryService inventoryService;

    @Operation(summary = "get current active inventory (it returns 404 if no active inventory now")
    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object>  getCurrentInventory(
            @RequestParam(required = false) List<String> fields) {
        log.info(TAG + "getCurrentInventory");
        return inventoryService.getCurrentInventory(fields);
    }



    @Operation(summary = "Get inventory by id")
    @Secured("ROLE_ADMIN")
    @GetMapping("/{inventory_id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getInventoryById(
            @PathVariable("inventory_id") Long inventoryId,
            @RequestParam(required = false) List<String> fields) {
        log.info(TAG + "getInventoryById");
        return inventoryService.getInventoryById(inventoryId, fields);
    }



    @Operation(summary = "Get inventories")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getInventories(
            @RequestParam(required = false) List<String> fields) {
        log.info(TAG + "getInventories");
        return inventoryService.getInventories(fields);
    }




    @Operation(summary = "Create new inventory")
    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInventory(
            @RequestBody InventoryDTO inventoryDto) {
        log.info(TAG + "createInventory");
        inventoryService.createInventory(SecHolder.getUserId(), inventoryDto);
    }

    @Operation(summary = "Update inventory")
    @Secured("ROLE_ADMIN")
    @PutMapping("/{inventory_id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateInventory(
            @PathVariable("inventory_id") Long inventoryId, @RequestBody InventoryDTO inventoryDTO) {
        log.info(TAG + "updateInventory");
        inventoryService.updateInventory(SecHolder.getUserId(), inventoryId, inventoryDTO);
    }
}
