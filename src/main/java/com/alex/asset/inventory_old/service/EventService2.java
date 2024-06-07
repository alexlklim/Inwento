package com.alex.asset.inventory_old.service;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.exceptions.shared.ObjectAlreadyExistException;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.inventory.dto.EventDTO;
import com.alex.asset.inventory.mapper.EventMapper;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory_old.EventMapper2;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.repo.ProductRepo;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService2 {
    private final String TAG = "EVENT_SERVICE - ";
    private final LogService logService;
    private final EventMapper2 eventMapper;
    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final InventoryRepo inventoryRepo;
    private final BranchRepo branchRepo;
    private final ProductRepo productRepo;
    private final ScannedProductRepo scannedProductRepo;

    @SneakyThrows
    public Map<String, Object> getEvent(Long eventId, List<String> eventFields) {
        log.info(TAG + "Get event with id {}", eventId);
        if (eventFields == null || eventFields.isEmpty()) eventFields = UtilEvent.getAll();
        Event event = eventRepo.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event not found with id " + eventId)
        );
        return eventMapper.toDTOWithCustomFields(event, eventFields);
    }


    @SneakyThrows
    public List<Map<String, Object>> getEventsForInventory(
            Long inventoryId, String mode, List<String> eventFields, Long userId) {
        Inventory inventory = inventoryRepo.findById(inventoryId).orElseThrow(
                () -> new ResourceNotFoundException("Inventory not found with id " + inventoryId));
        List<Event> events = null;
        if (Role.ADMIN == Role.fromString(mode)) {
            if (Role.ADMIN == userRepo.getUser(userId).getRoles())
                events = eventRepo.getAllEventsByInventory(inventory);
        } else events = eventRepo.getActiveEventsByInventory(inventory);
        if (events != null) {
            return events.stream().map(event -> eventMapper.toDTOWithCustomFields(event, eventFields)).toList();
        } else throw new ResourceNotFoundException("Inventory not found with id " + inventoryId);
    }


    @SneakyThrows
    public void updateVisibility(Long userId, DtoActive dto) {
        log.info(TAG + "Update visibility of event by user with id {} for event with id {}", userId, dto.getId());
        Event event = eventRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Event with this id not found")
        );
        event.setActive(dto.isActive());
        eventRepo.save(event);
        logService.addLog(userId, Action.UPDATE, Section.EVENT, dto.toString());

    }


    private void createDBSnapshotForEvent(Event event) {
        log.info(TAG + "Create DB SNAPSHOT for event");
        Branch branch = event.getBranch();
        List<Product> products = productRepo.findAllByBranch(branch);
        for (Product product : products) {
            ScannedProduct scannedProduct = new ScannedProduct().toBuilder()
                    .product(product).event(event).isScanned(false).build();
            scannedProductRepo.save(scannedProduct);
        }
    }


    //////
    @SneakyThrows
    public Map<String, Object> createEvent(Long userId, EventDTO dto) {
        log.info(TAG + "Create event by user with id {}", userId);
        Branch branch = branchRepo.findById(dto.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch with id " + dto.getBranchId() + " not found"));
        Event event = createEventForBranch(userRepo.getUser(userId), branch, dto.getInfo());
        return eventMapper.toDTOWithCustomFields(event, UtilEvent.getAll());
    }


    @SneakyThrows
    public void createEventsForInventory(Long userId) {
        log.info(TAG + "createEventsForInventory");
        User user = userRepo.getUser(userId);
        List<Branch> branches = branchRepo.getActive();
        for (Branch branch : branches) {
            createEventForBranch(user, branch);
        }
    }

    @SneakyThrows
    private void createEventForBranch(User user, Branch branch) {
        createEventForBranch(user, branch, "Default Info");
    }

    @SneakyThrows
    private Event createEventForBranch(User user, Branch branch, String info) {
        log.info(TAG + "createEventForBranch");
        Inventory inventory = inventoryRepo.getCurrentInventory(LocalDate.now())
                .orElseThrow(() -> new ResourceNotFoundException("No active inventory now"));

        if (eventRepo.existsByBranchAndInventory(branch, inventory))
            throw new ObjectAlreadyExistException("Event for this branch already exists");

        Event event = new Event();
        event.setActive(true);
        event.setInventory(inventory);
        event.setUser(user);
        event.setBranch(branch);
        event.setInfo(info);
        Event eventFrommDB = eventRepo.save(event);
        logService.addLog(user.getId(), Action.CREATE, Section.EVENT, info);
        createDBSnapshotForEvent(eventFrommDB);
        return event;
    }


}
