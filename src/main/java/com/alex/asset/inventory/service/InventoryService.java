package com.alex.asset.inventory.service;

import com.alex.asset.exceptions.inventory.InventIsAlreadyInProgressException;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.dto.InventoryDTO;
import com.alex.asset.inventory.mapper.InventoryMapper;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.utils.dictionaries.UtilsInventory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {

    private final String TAG = "INVENTORY_SERVICE - ";
    private final InventoryMapper inventoryMapper;
    private final LogService logService;
    private final NotificationService notificationService;
    private final EventService eventService;
    private final InventoryRepo inventoryRepo;
    private final DBSnapshotProvider snapshotProvider;


    public Map<String, Object> getInventoryById(Long inventoryId, List<String> fields) {
        log.info(TAG + "getInventoryById");
        return inventoryMapper.toDTOWithCustomFields(
                inventoryRepo.findById(inventoryId).orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id " + inventoryId)),
                fields == null ? UtilsInventory.getAllFields() : fields
        );
    }


    public List<Map<String, Object>> getInventories(List<String> fields) {
        log.info(TAG + "getInventories");
        return inventoryMapper.toDTOsWithCustomFields(
                inventoryRepo.findAll(),
                fields == null ? UtilsInventory.getAllFields() : fields
        );
    }



    @SneakyThrows
    public void createInventory(Long userId, InventoryDTO dto) {
        log.info(TAG + "createInventory");
        if (inventoryRepo.getCurrentInventory(LocalDate.now()).orElse(null) != null)
            throw new InventIsAlreadyInProgressException("Inventory is active at this moment");
        Inventory inventory = new Inventory();
        inventory.setActive(true);
        inventory.setStartDate(dto.getStartDate());
        inventory.setFinished(dto.isFinished());
        if (dto.isFinished()) inventory.setFinishDate(LocalDate.now());
        else inventory.setFinishDate(null);
        inventory.setInfo(dto.getInfo());
        inventoryRepo.save(inventory);
        logService.addLog(userId, Action.CREATE, Section.INVENTORY, dto.toString());
        notificationService.sendSystemNotificationToAllUsers(Reason.PLANNED_INVENTORY);
        snapshotProvider.createEventsForInventory(userId);
    }


    @SneakyThrows
    public void updateInventory(Long userId, Long inventoryId, InventoryDTO dto) {
        log.info(TAG + "updateInventory");
        Inventory inventory = getInventoryById(inventoryId);
        inventory.setStartDate(dto.getStartDate());
        inventory.setFinished(dto.isFinished());
        if (dto.isFinished()) inventory.setFinishDate(LocalDate.now());
        else inventory.setFinishDate(null);
        inventory.setInfo(dto.getInfo());
        inventoryRepo.save(inventory);
        logService.addLog(userId, Action.UPDATE, Section.INVENTORY, dto.toString());
    }





    @SneakyThrows
    private Inventory getInventoryById(Long inventoryId){
        log.info(TAG + "getInventoryById");
        return  inventoryRepo.findById(inventoryId).orElseThrow(
                () -> new ResourceNotFoundException("Inventory with id " + inventoryId + " not found"));
    }

    @SneakyThrows
    public Map<String, Object>  getCurrentInventory(List<String> fields) {
        log.info(TAG + "getCurrentInventory");
        return getInventoryById(
                inventoryRepo.getCurrentInventory(LocalDate.now())
                        .orElseThrow(() -> new ResourceNotFoundException("No active inventory at this moment"))
                        .getId(),
                fields == null ? UtilsInventory.getAllFields() : fields
        );
    }
}
