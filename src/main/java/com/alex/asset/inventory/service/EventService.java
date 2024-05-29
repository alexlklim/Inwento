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
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.UtilsEvent;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.exceptions.shared.ObjectAlreadyExistException;
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
     private final ProductService productService;
     private final LogService logService;
     private final EventMapper eventMapper;
     private final EventRepo eventRepo;
     private final UserRepo userRepo;
     private final InventoryRepo inventoryRepo;
     private final BranchRepo branchRepo;
     private final UnknownProductRepo unknownProductRepo;
     private final LocationRepo locationRepo;
     private final ProductRepo productRepo;
     private final ScannedProductRepo scannedProductRepo;

    @SneakyThrows
    public Map<String, Object> getEvent(Long eventId, List<String> eventFields) {
        log.info(TAG + "Get event with id {}", eventId);
        if (eventFields == null || eventFields.isEmpty()) eventFields = UtilsEvent.getAll();
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
    public Map<String, Object> createEvent(Long userId, EventDTO dto) {
        log.info(TAG + "Create event by user with id {}", userId);
        Branch branch = branchRepo.findById(dto.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch with id " + dto.getBranchId() + " not found"));
        Event event = createEventForBranch(userRepo.getUser(userId), branch, dto.getInfo());
        return eventMapper.toDTOWithCustomFields(event, UtilsEvent.getAll());
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


    public void addProductsToEventByRfidCode(List<String> listOfCodes, Long eventId, Long userId) {
        log.info(TAG + "Adding products to event by rfid code by user with id {}", userId);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        System.out.println("EVENT = " + event.getId());
        for (String rfidCode : listOfCodes) {
            System.out.println("CODE: " + rfidCode);
            Product product = productRepo.getByRfidCode(rfidCode).orElse(null);
            if (product == null) {
                log.error(TAG + "PRODUCT IS NULL");
                continue;
            }
            System.out.println("PRODUCT: " + product.getRfidCode());

            ScannedProduct scannedProduct = scannedProductRepo.findByProductAndEvent(product, event).orElse(null);
            if (scannedProduct == null){
                // if scanned product for this product not found -> create it avutomatically
                scannedProduct = new ScannedProduct().toBuilder()
                        .user(userRepo.getUser(userId))
                        .event(event)
                        .isScanned(true)
                        .product(product)
                        .build();
            }
            scannedProduct.setIsScanned(true);

             
            scannedProductRepo.save(scannedProduct);
        }
    }

    @SneakyThrows
    @Transactional
    public void addProductsToEventByBarCode(
            List<Map<String, Object>> inventoryData, Long eventId, Long locationId, Long userId) {
        log.info(TAG + "Adding products to event by bar code by user with id {}", userId);
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event with id " + eventId + " not found"));
        Location location = locationRepo.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id " + locationId + " not found"));

        for (Map<String, Object> map : inventoryData) {
            Product product;
            if (map.containsKey("code")) {
                String code = (String) map.get("code");
                product = productRepo.getByBarCode(code).orElse(null);

                if (product != null) handleScannedProduct(product, map, event, location, userId);
                else handleUnknownProduct(code, event, userId);
            }

            if (map.containsKey("id")) {
                Object idObject = map.get("id");
                if (idObject instanceof Number) {
                    Long productId = ((Number) idObject).longValue();
                    product = productRepo.findById(productId)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
                    handleScannedProduct(product, map, event, location, userId);
                }
            }

        }

    }

    private void handleScannedProduct(
            Product product, Map<String, Object> map, Event event, Location location, Long userId) {
        log.info(TAG + "Add product to scanned product");
        if (product.getLocation() != location) {
            productService.moveProductByLocation(product, location, userId);
        }
        if (product.getBranch() != event.getBranch()) {
            log.error(TAG + "Product doesn't belong to current branch");

//            productService.moveProductByBranch(product, event, userRepo.getUser(userId));
            return;
        }
        ScannedProduct scannedProduct = scannedProductRepo.findScannedProductByEventAndProduct(event, product)
                .orElse(null);
        if (scannedProduct == null) return;
        scannedProduct.setIsScanned(true);
        scannedProduct.setUser(userRepo.getUser(userId));

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
            log.info(TAG + "Data about location was updated for product with id {} ", product.getId());
        });

        
        scannedProductRepo.save(scannedProduct);
    }


    @SneakyThrows
     void handleUnknownProduct(String code, Event event, Long userId) {
        log.info(TAG + "Add unknown product");
        UnknownProduct unknownProduct = unknownProductRepo.findByCode(code).orElse(null);
        if (unknownProduct == null) {
            unknownProduct = new UnknownProduct().toBuilder()
                    .event(event).user(userRepo.getUser(userId)).code(code).typeCode(CodeType.BAR_CODE)
                    .build();
        } else {
            log.error(TAG + "Unknown product {} is already exist", unknownProduct.getCode());
            if (unknownProduct.getEvent() != event) {
                log.error(TAG + "Move unknown product to current branch {}", event.getBranch());
                unknownProduct.setEvent(event);
                unknownProduct.setTypeCode(CodeType.BAR_CODE);
                unknownProduct.setUser(userRepo.getUser(userId));
            }
        }
        unknownProductRepo.save(unknownProduct);
    }

    @SneakyThrows
    public void createEventsForInventory(Long userId) {
        log.info(TAG + "Create events for Inventory automatically");
        User user = userRepo.getUser(userId);
        List<Branch> branches = branchRepo.getActive();
        for (Branch branch : branches){
            createEventForBranch(user, branch);
        }
    }

    @SneakyThrows
     void createEventForBranch(User user, Branch branch) {
        createEventForBranch(user, branch, "Default Info");
    }

    @SneakyThrows
     Event createEventForBranch(User user, Branch branch, String info) {
        log.info(TAG + "Create event for Inventory automatically");
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
        logService.addLog(user.getId(), Action.CREATE, Section.EVENT, info);
        Event eventFrommDB = eventRepo.save(event);
        createDBSnapshotForEvent(eventFrommDB);

        return event;
    }

     void createDBSnapshotForEvent(Event event) {
        log.info(TAG + "Create DB SNAPSHOT for event");
        Branch branch = event.getBranch();
        List<Product> products = productRepo.findAllByBranch(branch);
        for (Product product: products){
            ScannedProduct scannedProduct = new ScannedProduct().toBuilder()
                    .product(product).event(event).isScanned(false).build();
            scannedProductRepo.save(scannedProduct);
        }
    }
}
