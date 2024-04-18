package com.alex.asset.inventory.service;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.configure.repo.LocationRepo;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.CodeType;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.inventory.domain.event.UnknownProduct;
import com.alex.asset.inventory.dto.EventDTO;
import com.alex.asset.inventory.mapper.EventMapper;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.Utils;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import com.alex.asset.utils.exceptions.errors.user_error.ObjectAlreadyExistException;
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
public class EventService {
    private final String TAG = "EVENT_SERVICE - ";
    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final InventoryRepo inventoryRepo;
    private final BranchRepo branchRepo;
    private final UnknownProductRepo unknownProductRepo;
    private final ProductService productService;
    private final LogService logService;
    private final EventMapper eventMapper;

    private final LocationRepo locationRepo;
    private final ProductRepo productRepo;
    private final ScannedProductRepo scannedProductRepo;

    @SneakyThrows
    public Map<String, Object> getEvent(Long eventId, List<String> eventFields) {
        log.info(TAG + "Get event with id {}", eventId);
        if (eventFields == null || eventFields.isEmpty()) eventFields = Utils.EVENT_FIELDS;
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        return eventMapper.toDTOWithCustomFields(event, eventFields);
    }


    @SneakyThrows
    public List<Map<String, Object>> getEventsForInventory(Long inventoryId, String mode, List<String> eventFields, Long userId) {
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
    public Map<String, Object> createEvent(Long userId, EventDTO dto) {
        log.info(TAG + "Create event by user with id {}", userId);
        Inventory inventory = inventoryRepo.getCurrentInvent(LocalDate.now())
                .orElseThrow(() -> new ResourceNotFoundException("No active inventory now"));
        Branch branch = branchRepo.findById(dto.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch with id " + dto.getBranchId() + " not found"));
        if (eventRepo.existsByBranchAndInventory(branch, inventory)) throw new ObjectAlreadyExistException("Event for this branch already exists");


        Event event = new Event();
        event.setActive(true);
        event.setInventory(inventory);
        event.setUser(userRepo.getUser(userId));
        event.setBranch(branch);
        event.setInfo(dto.getInfo());
        logService.addLog(userId, Action.CREATE, Section.EVENT, dto.toString());
        eventRepo.save(event);
        return eventMapper.toDTOWithCustomFields(event, Utils.EVENT_FIELDS);
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

    @SneakyThrows
    private List<UnknownProduct> getUnknownProductsForEvent(Long eventId) {
        log.info(TAG + "Get unknown products of event with id {}", eventId);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + eventId + " was not found"));
        return unknownProductRepo.findByEvent(event);
    }


    @SneakyThrows
    @Transactional
    public void addProductsToEventByBarCode(
            List<Map<String, Object>> inventoryData, Long eventId, Long locationId, String typeCode, Long userId) {
        log.info("Adding products to event by bar code by user with id {}", userId);
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event with id " + eventId + " not found"));
        Location location = locationRepo.findById(locationId).orElseThrow(() -> new ResourceNotFoundException("Location with id " + locationId + " not found"));
        for (Map<String, Object> map : inventoryData) {
            Product product = null;
            if (map.containsKey("code")) {
                String code = (String) map.get("code");
                CodeType codeType = CodeType.fromString(typeCode);
                if (codeType == CodeType.BAR_CODE)
                    product = productRepo.getByBarCode(code).orElse(null);
                else if (codeType == CodeType.RFID_CODE) {
                    product = productRepo.getByRfidCode(code).orElse(null);
                } else handleUnknownProduct(code, codeType, event, userId);
                if (product != null) handleScannedProduct(product, map, event, location, userId);
            }
        }

    }

    private void handleScannedProduct(Product product, Map<String, Object> map, Event event, Location location, Long userId) {
        if (product.getLocation() != location)
            productService.moveProduct(product, product.getBranch(), location, userId);
        ScannedProduct scannedProduct = new ScannedProduct();
        scannedProduct.setUser(userRepo.getUser(userId));
        scannedProduct.setProduct(product);
        scannedProduct.setEvent(event);
        map.forEach((key, value) -> {
            switch (key) {
                case "longitude":
                    product.setLongitude(((Number) value).doubleValue());
                    break;
                case "latitude":
                    product.setLatitude(((Number) value).doubleValue());
                    break;
                default:
                    break;
            }
            productService.save(product);
            scannedProductRepo.save(scannedProduct);
        });
    }

    @SneakyThrows
    private void handleUnknownProduct(String code, CodeType codeType, Event event, Long userId) {
        log.info("Unknown product with code {} doesn't exist in DB", code);
        UnknownProduct unknownProduct = unknownProductRepo.findByCode(code).orElse(null);
        if (unknownProduct == null) {
            unknownProduct = new UnknownProduct();
            unknownProduct.setEvent(event);
            unknownProduct.setUser(userRepo.getUser(userId));
            unknownProduct.setCode(code);
            unknownProduct.setTypeCode(codeType);
        } else {
            if (unknownProduct.getEvent() != event) {
                unknownProduct.setEvent(event);
            }
        }
        unknownProductRepo.save(unknownProduct);
    }


}
