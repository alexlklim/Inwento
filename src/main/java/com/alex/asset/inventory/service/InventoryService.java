package com.alex.asset.inventory.service;


import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.dto.InventoryDto;
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
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.utils.UtilEvent;
import com.alex.asset.utils.UtilProduct;
import com.alex.asset.utils.domain.BaseEntity;
import com.alex.asset.exceptions.inventory.InventIsAlreadyInProgress;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final InventoryMapper inventoryMapper;
    private final EventRepo eventRepo;
    private final UnknownProductRepo unknownProductRepo;
    private final ScannedProductRepo scannedProductRepo;


    @SneakyThrows
    public void createInventory(Long userId, InventoryDto dto) {
        log.info(TAG + "User {} create new inventory", userId);


        if (inventoryRepo.getCurrentInventory(LocalDate.now()).orElse(null) != null) {
            throw new InventIsAlreadyInProgress("Inventory is active at this moment");
        }


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

        eventService.createEventsForInventory(userId);
    }


    @SneakyThrows
    public void updateInventory(Long userId, Long inventoryId, InventoryDto dto) {
        log.info(TAG + "Update inventory by user with id {}", userId);
        Inventory inventory = inventoryRepo.findById(inventoryId).orElseThrow(
                () -> new ResourceNotFoundException("Inventory with id " + inventoryId + " not found"));
        inventory.setStartDate(dto.getStartDate());
        inventory.setFinished(dto.isFinished());
        if (dto.isFinished()) inventory.setFinishDate(LocalDate.now());
        else inventory.setFinishDate(null);
        inventory.setInfo(dto.getInfo());
        inventoryRepo.save(inventory);
        logService.addLog(userId, Action.UPDATE, Section.INVENTORY, dto.toString());
    }


    public Map<String, Object> getFullInventoryById(Long inventoryId, Long userId) {
        Map<String, Object> inventoryMap = new HashMap<>();
        inventoryMap.put("inventory_dto", getInventoryById(inventoryId, userId));
        // get events for this inventory
        // unknown -> get all unknown_products where event_id in (event ids)
        // scanned -> get all scanned products where id in (event ids)
        // sort products by fields isScanned (true or false) and add their product (FK) to proper list

        List<Long> eventIds = eventRepo.getActiveEventsByInventory(
                inventoryRepo.findById(inventoryId).orElseThrow(
                        () -> new ResourceNotFoundException("Event not found with id " + inventoryId)
                )
        ).stream().map(BaseEntity::getId).toList();

        inventoryMap.put("unknown_products", unknownProductRepo.getAllByEvents(eventIds));
        inventoryMap.put("scanned_products",
                scannedProductRepo.getScannedProductsByEvents(eventIds, true).stream().map(
                        scannedProduct -> ProductMapper.toDTOWithCustomFields(
                                scannedProduct.getProduct(),
                                UtilProduct.getFieldsForReport())
                ).toList()
        );
        inventoryMap.put("not_scanned_products",
                scannedProductRepo.getScannedProductsByEvents(eventIds, false).stream().map(
                        scannedProduct -> ProductMapper.toDTOWithCustomFields(
                                scannedProduct.getProduct(),
                                UtilProduct.getFieldsForReport())
                ).toList()
        );

        inventoryMap.put("total_product_amount", scannedProductRepo.countByEventsId(eventIds));
        inventoryMap.put("scanned_product_amount", scannedProductRepo.countByEventsIdAndIsScanned(eventIds, true));
        inventoryMap.put("unknown_product_amount", unknownProductRepo.countProductsByEventIds(eventIds));

        return inventoryMap;
    }

    @SneakyThrows
    public InventoryDto getInventoryById(Long inventoryId, Long userId) {
        log.info(TAG + "Get inventory by id {}", inventoryId);
        Inventory inventory = inventoryRepo.findById(inventoryId).orElseThrow(
                () -> new ResourceNotFoundException("Inventory with id " + inventoryId + " not found"));
        InventoryDto inventoryDto = inventoryMapper.toDto(inventory);

        List<Event> events = eventRepo.getActiveEventsByInventory(inventory);
        int unknownProductAmount = 0;
        int totalProductAmount = 0;
        int scannedProductAmount = 0;

        for (Event event : events) {
            unknownProductAmount += unknownProductRepo.countProductsByEventId(event.getId());
            totalProductAmount += productRepo.countProductsByBranchId(event.getBranch().getId());
            scannedProductAmount += scannedProductRepo.countByEventIdAndIsScanned(event, true);
        }

        inventoryDto.setScannedProductAmount(scannedProductAmount);
        inventoryDto.setUnknownProductAmount(unknownProductAmount);
        inventoryDto.setTotalProductAmount(totalProductAmount);
        inventoryDto.setEvents(
                eventService.getEventsForInventory(inventoryId, "emp", UtilEvent.getFieldsSimpleView(), userId)
        );

        return inventoryDto;
    }

    private final EventService eventService;

    @SneakyThrows
    public InventoryDto getCurrentInventory(Long userId) {
        log.info(TAG + "Get current inventory");
        return getInventoryById(
                inventoryRepo.getCurrentInventory(LocalDate.now())
                        .orElseThrow(() -> new ResourceNotFoundException("No active inventory at this moment"))
                        .getId(),
                userId
        );
    }


    public List<InventoryDto> getInventories() {
        // get all active inventories
        return inventoryRepo.findAll().stream().map(inventoryMapper::toDto).toList();
    }
}
