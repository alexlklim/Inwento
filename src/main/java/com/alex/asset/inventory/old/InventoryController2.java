package com.alex.asset.inventory.old;

import com.alex.asset.inventory.dto.InventoryDTO;
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
@RequestMapping("/api/v1/inventory")
@Tag(name = "Inventory Controller", description = "Inventory API")
public class InventoryController2 {
     private final String TAG = "INVENTORY_CONTROLLER - ";

     private final InventoryService2 inventoryService2;


     // get list of inventories with custom fields
    // get inventory by id with custom fields
    // get current inventory by id with custom fields


    //   create new inventory
    // update inventory



    @Operation(summary = "Get inventory by id")
    @Secured("ROLE_ADMIN")
    @GetMapping("/{inventory_id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDTO getInventoryById(
            @PathVariable("inventory_id") Long inventoryId) {
        log.info(TAG + "Get inventory with id {}", inventoryId);
        return inventoryService2.getInventoryById(inventoryId, SecHolder.getUserId());
    }


    @Operation(summary = "Get full information about inventory by id (include all scanned/ not scanned/ unknown products)")
    @GetMapping("/{inventory_id}/full")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getFullInventoryById(
            @PathVariable("inventory_id") Long inventoryId) {
        log.info(TAG + "Get inventory with id {}", inventoryId);
        return inventoryService2.getFullInventoryById(inventoryId, SecHolder.getUserId());
    }

    @Operation(summary = "Get inventories")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDTO> getInventories() {
        log.info(TAG + "Get all inventories");
        return inventoryService2.getInventories();
    }



    @Operation(summary = "get current active inventory (it returns 404 if no active inventory now")
    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDTO getCurrentInventory() {
        log.info(TAG + "get current inventory");
        return inventoryService2.getCurrentInventory(SecHolder.getUserId());
    }


    @Operation(summary = "Create new inventarization")
    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @RequestBody InventoryDTO inventoryDto) {
        log.info(TAG + "Create inventory");
        inventoryService2.createInventory(SecHolder.getUserId(), inventoryDto);
    }



    @Operation(summary = "Update inventarization")
    @Secured("ROLE_ADMIN")
    @PutMapping("/{inventory_id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @PathVariable("inventory_id") Long inventoryId, @RequestBody InventoryDTO inventoryDto) {
        log.info(TAG + "Update inventory");
        inventoryService2.updateInventory(SecHolder.getUserId(), inventoryId, inventoryDto);
    }



}
