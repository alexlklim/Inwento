package com.alex.asset.inventory.service;


import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.configure.repo.LocationRepo;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.mapper.EventMapper;
import com.alex.asset.inventory.mapper.InventoryMapper;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dictionaries.UtilEvent;
import com.alex.asset.utils.dto.DtoActive;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final String TAG = "EVENT_SERVICE - ";
    private final EventMapper eventMapper;
    private final ProductService productService;
    private final LogService logService;

    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final InventoryRepo inventoryRepo;
    private final BranchRepo branchRepo;
    private final UnknownProductRepo unknownProductRepo;
    private final LocationRepo locationRepo;
    private final ProductRepo productRepo;
    private final ScannedProductRepo scannedProductRepo;

    @SneakyThrows
    public Map<String, Object> getEventById(Long eventId, List<String> fields, Long userId) {
        log.info(TAG + "getEvent");
        return eventMapper.toDTOWithCustomFields(
                eventRepo.getEventByIdAndActive(
                        eventId,
                        Role.ADMIN == userRepo.getUser(userId).getRoles() ? true : false
                ).orElseThrow(
                        () -> new ResourceNotFoundException("Event not found with id " + eventId)),
                fields == null ? UtilEvent.getAll() : fields);
    }

    @SneakyThrows
    @Transactional
    public List<Map<String, Object>> getEventsForInventory(
            Long inventoryId, String accessMode, List<String> eventFields, Long userId) {
        log.info(TAG + "getEventsForInventory");
        Inventory inventory = getInventoryById(inventoryId);
        List<Event> events = (Role.ADMIN == Role.fromString(accessMode)
                && Role.ADMIN == userRepo.getUser(userId).getRoles())
                ? eventRepo.getAllEventsByInventory(inventory)
                : eventRepo.getActiveEventsByInventory(inventory);
        return eventMapper.toDTOsWithCustomFields(events,  eventFields == null ? UtilEvent.getAll() : eventFields);
    }

    @SneakyThrows
    public void updateVisibility(Long userId, DtoActive dto) {
        log.info(TAG + "updateVisibility");
        Event event = eventRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Event with this id not found")
        );
        event.setActive(dto.isActive());
        eventRepo.save(event);
        logService.addLog(userId, Action.UPDATE, Section.EVENT, dto.toString());

    }



    private Inventory getInventoryById(Long inventoryId){
        log.info(TAG + "getInventoryById");
        return inventoryRepo.findById(inventoryId).orElseThrow(
                () -> new ResourceNotFoundException("Inventory not found with id " + inventoryId));
    }
}
