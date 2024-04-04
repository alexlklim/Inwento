package com.alex.asset.inventory.service;


import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.dto.EventV2Get;
import com.alex.asset.inventory.dto.InventoryDto;
import com.alex.asset.inventory.mapper.InventoryMapper;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.exceptions.errors.InventIsAlreadyInProgress;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {
    private final String TAG = "Inventory_SERVICE - ";
    private final InventoryRepo inventoryRepo;
    private final ProductRepo productRepo;
    private final LogService logService;
    private final NotificationService notificationService;
    private final EventService eventService;
    private final InventoryMapper inventoryMapper;


    public boolean isInventoryNow() {
        log.info(TAG + "Check is any inventory active now or not");
        return inventoryRepo.isInventoryNow(LocalDate.now());
    }


    @SneakyThrows
    public void startInventory(Long userId, InventoryDto dto) {
        log.info(TAG + "User {} create new inventory", userId);
        if (isInventoryNow()) throw new InventIsAlreadyInProgress("Inventory is already in progress this time");
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

    }


    @SneakyThrows
    public void updateInventory(Long userId, Long inventoryId, InventoryDto dto) {
        log.info(TAG + "Update inventory by user with id {}", userId);
        Inventory inventory = inventoryRepo.findById(inventoryId).orElseThrow(
                () -> new ResourceNotFoundException("Inventory with id " + inventoryId + " not found")
        );
        inventory.setStartDate(dto.getStartDate());
        inventory.setFinished(dto.isFinished());
        if (dto.isFinished()) inventory.setFinishDate(LocalDate.now());
        else inventory.setFinishDate(null);
        inventory.setInfo(dto.getInfo());
        inventoryRepo.save(inventory);

        logService.addLog(userId, Action.UPDATE, Section.INVENTORY, dto.toString());

    }


    @SneakyThrows
    public void changeVisibility(Long userId, DtoActive dto) {
        log.info(TAG + "Change visibility of inventory with id {} by user with id {}", dto.getId(), userId);
        Inventory inventory = inventoryRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Inventory with id " + dto.getId() + " not found")
        );
        inventory.setActive(dto.isActive());
        inventoryRepo.save(inventory);

        logService.addLog(userId, Action.UPDATE, Section.INVENTORY, dto.toString());
    }

    @SneakyThrows
    public InventoryDto getInventoryById(Long inventoryId) {
        log.info(TAG + "Get inventory by id {}", inventoryId);
        InventoryDto inventory = inventoryMapper.toDto(inventoryRepo.findById(inventoryId).orElseThrow(
                () -> new ResourceNotFoundException("Inventory with id " + inventoryId + " not found"))
        );
        List<EventV2Get> events = eventService.getAllEventsForSpecificInvent(inventory.getId());
        inventory.setTotalProductAmount(productRepo.getActiveProductCount());

        inventory.setUnknownProductAmount(events
                .stream()
                .mapToLong(EventV2Get::getUnknownProductAmount)
                .sum());
        inventory.setScannedProductAmount(events
                .stream()
                .mapToLong(EventV2Get::getScannedProductAmount)
                .sum());
        return inventory;
    }


    @SneakyThrows
    public InventoryDto getCurrentInventory() {
        log.info(TAG + "Get current inventory");
        return getInventoryById(inventoryRepo.getCurrentInvent(LocalDate.now()).orElseThrow(
                () -> new ResourceNotFoundException("No active inventory at this moment")).getId()
        );
    }


}
