package com.alex.asset.inventory.service;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.InventoryStatus;
import com.alex.asset.inventory.domain.event.UnknownProduct;
import com.alex.asset.inventory.dto.EventV1Create;
import com.alex.asset.inventory.dto.EventV2Get;
import com.alex.asset.inventory.mapper.EventMapper;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.dto.ProductV2Dto;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.mappers.ProductUtils;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import com.alex.asset.utils.exceptions.errors.user_error.ObjectAlreadyExistException;
import com.alex.asset.utils.exceptions.errors.user_error.UserIsNotOwnerOfEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final ProductMapper productMapper;
    private final EventMapper eventMapper;

    @SneakyThrows
    public EventV2Get getEvent(Long eventId) {
        log.info(TAG + "Get event with id {}", eventId);
        EventV2Get dto = eventMapper.toDto(eventRepo.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event with id " + eventId + " not found"))
        );
        dto.setUnknownProducts(getUnknownProductsForEvent(eventId));
        dto.setProducts(getAllProductsV2ForBranch(eventId));

        dto.setUnknownProductAmount(dto.getUnknownProducts().size());
        dto.setTotalProductAmount(dto.getProducts().size());

        dto.setScannedProductAmount(dto.getProducts().stream()
                .filter(product -> product.getInventoryStatus() == InventoryStatus.SCANNED)
                .mapToInt(product -> 1)
                .sum());
        return dto;
    }


    @SneakyThrows
    public List<EventV2Get> getEventsForSpecificUserAndInvent(Long userId, Long inventId) {
        log.info(TAG + "Get event for user with id {} and invent with id {}", userId, inventId);
        return eventRepo.findAllByUserAndInventory(
                        userRepo.getUser(userId), inventoryRepo.findById(inventId).orElseThrow(
                                () -> new ResourceNotFoundException("Invent with id " + inventId + " not found")))
                .stream()
                .map(this::mapEventToDto)
                .toList();
    }


    private EventV2Get mapEventToDto(Event event) {
        EventV2Get dto = eventMapper.toDto(event);
        dto.setUnknownProductAmount(getUnknownProductsForEvent(event.getId()).size());
        dto.setScannedProductAmount(ProductUtils.countScannedProducts(getAllProductsV2ForBranch(event.getId())));
        dto.setTotalProductAmount(getAllProductsV2ForBranch(event.getId()).size());
        return dto;
    }

    @SneakyThrows
    public List<EventV2Get> getAllEventsForSpecificInvent(Long inventId) {
        log.info(TAG + "Get event for invent with id {}", inventId);
        return eventRepo.findAllByInventory(inventoryRepo.findById(inventId).orElseThrow(
                        () -> new ResourceNotFoundException("Invent with id " + inventId + " not found")))
                .stream()
                .map(this::mapEventToDto)
                .toList();
    }

    @SneakyThrows
    public EventV2Get createEvent(Long userId, EventV1Create dto) {
        log.info(TAG + "Create event by user with id {}", userId);

        // if user already create event for this inventory and this branch return exception
        if (eventRepo.existsByUserAndBranchAndInventory(
                userRepo.getUser(userId),
                branchRepo.findById(dto.getBranchId()).orElseThrow(
                        () -> new ResourceNotFoundException("Branch with id " + dto.getBranchId() + " not found")
                ),
                inventoryRepo.getCurrentInvent(LocalDate.now()).orElseThrow(
                        () -> new ResourceNotFoundException("No active inventory now")
                )
        )) {
            throw new ObjectAlreadyExistException("User already create event for this branch");
        }
        Event event = new Event();
        event.setActive(true);
        event.setInventory(inventoryRepo.getCurrentInvent(LocalDate.now())
                .orElseThrow(
                        () -> new ResourceNotFoundException("No active inventory at this time"))
        );
        event.setUser(userRepo.getUser(userId));
        event.setBranch(branchRepo.findById(dto.getBranchId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Branch with id " + dto.getBranchId() + " not found"))
        );
        event.setInfo(dto.getInfo());
        event.setProducts(new ArrayList<>());

        logService.addLog(userId, Action.CREATE, Section.EVENT, dto.toString());

        return getEvent(eventRepo.save(event).getId());
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


    private List<ProductV2Dto> getAllProductsV2ForBranch(Long eventId) {
        log.info(TAG + "Get all products by specific branch of event with id {}", eventId);
        Branch branch = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + eventId + " was not found"))
                .getBranch();

        return Stream.concat(getScannedProductsForBranch(branch).stream(),
                        getNotScannedProductForBranch(branch).stream())
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private List<ProductV2Dto> getScannedProductsForBranch(Branch branch) {
        log.info(TAG + "Get scanned products by branch with id {}", branch.getId());
        return eventRepo.findProductsByBranch(
                        branch,
                        inventoryRepo.getCurrentInvent(LocalDate.now()).orElseThrow(
                                () -> new ResourceNotFoundException("No active inventory now")
                        )).stream()
                .map(product -> productMapper.toProductV2Dto(product, InventoryStatus.SCANNED))
                .collect(Collectors.toList());

    }

    private List<ProductV2Dto> getNotScannedProductForBranch(Branch branch) {
        log.info(TAG + "Get not scanned products by branch with id {}", branch.getId());
        return productService.getActiveProductsByBranch(branch)
                .stream()
                .filter(product -> !eventRepo.findProductsByBranch(
                                branch,
                                inventoryRepo.getCurrentInvent(LocalDate.now())
                                        .orElseThrow(
                                                () -> new ResourceNotFoundException("Inventory not started yet"))
                        )
                        .contains(product))
                .map(product -> productMapper.toProductV2Dto(product, InventoryStatus.NOT_SCANNED))
                .collect(Collectors.toList());
    }


    @Transactional
    public void addProductsToEventByBarCode(Long userId, Long eventId, List<String> list) {
        log.info("Adding products to event by bar code by user with id {}", userId);

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + eventId + " not found"));

        User user = userRepo.getUser(userId);
        if (!event.getUser().equals(user))
            throw new UserIsNotOwnerOfEvent("User with id " + userId + " is not owner of event with id " + eventId);

        log.info("-----------------------------------------------------------------");

        for (String barCode : list) {
            log.info("Checking product with bar code {}", barCode);
            Product product = productService.getByBarCode(barCode).orElse(null);

            if (product != null) {
                log.info("Product with bar code {} exists in DB", barCode);

                if (event.getProducts().contains(product)) {
                    log.info("{} product already scanned in this event", barCode);
                } else {
                    handleNewProduct(event, product);
                }
            } else {
                handleUnknownProduct(event, barCode);
            }
        }

        eventRepo.save(event);

        logService.addLog(userId, Action.CREATE, Section.EVENT, "Add products to event ");
    }

    private void handleNewProduct(Event event, Product product) {
        log.info("{} product NOT scanned in this event", product.getBarCode());
        Event eventIfProductAlreadyScanned = eventRepo.findByProductId(product.getId(), event.getInventory()).orElse(null);
        if (eventIfProductAlreadyScanned == null) {
            log.info("{} product was NOT scanned in ANOTHER event", product.getBarCode());
            if (product.getBranch().equals(event.getBranch())) {
                log.info("{} product branch and event branch are similar", product.getBarCode());
            } else {
                log.info("{} product branch and event branch are NOT similar", product.getBarCode());
                productService.productMovedToAnotherBranch(product, event.getBranch());
            }
            event.getProducts().add(product);
        } else {
            log.info("{} product was scanned in ANOTHER event", product.getBarCode());
            if (!product.getBranch().equals(event.getBranch())) {
                log.info("{} product branch and event branch are NOT similar", product.getBarCode());
                productService.productMovedToAnotherBranch(product, event.getBranch());
                eventIfProductAlreadyScanned.getProducts().remove(product);
                eventRepo.save(eventIfProductAlreadyScanned);
            }
            event.getProducts().add(product);
        }
    }

    private void handleUnknownProduct(Event event, String barCode) {
        log.info("Unknown product with bar code {} doesn't exist in DB", barCode);
        UnknownProduct unknownProduct = unknownProductRepo.findByCode(barCode).orElse(null);
        if (unknownProduct == null) {
            log.info("Unknown product with bar code {} is NEW", barCode);
            UnknownProduct unknownProductNew = new UnknownProduct();
            unknownProductNew.setCode(barCode);
            unknownProductNew.setEvent(event);
            unknownProductRepo.save(unknownProductNew);
        } else {
            // check if product was scanned in this event
            if (unknownProduct.getEvent().equals(event)){
                log.info("Unknown product {} was scanned in this branch", barCode);
            } else {
                log.info("Unknown product {} was scanned in another event", barCode);
                unknownProduct.setEvent(event);
                unknownProductRepo.save(unknownProduct);
            }

        }
    }


}
