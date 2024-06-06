package com.alex.asset.inventory.service;

import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.mapper.InventoryMapper;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.utils.dictionaries.UtilsInventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InvService {

    private final String TAG = "INVENTORY_SERVICE - ";
    private final InventoryRepo inventoryRepo;
    private final EventRepo eventRepo;
    private final InventoryMapper inventoryMapper;


    public Map<String, Object> getInventoryById(Long inventoryId, List<String> fields) {
        log.info(TAG + "Get inventory by id {} by user}", inventoryId);
        return inventoryMapper.toDTOWithCustomFields(
                inventoryRepo.findById(inventoryId).orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id " + inventoryId)),
                fields == null ? UtilsInventory.getAllFields() : fields
        );
    }


    public List<Map<String, Object>> getInventories(List<String> fields) {
        return inventoryMapper.toDTOsWithCustomFields(
                inventoryRepo.findAll(),
                fields == null ? UtilsInventory.getAllFields() : fields
        );
    }
}
