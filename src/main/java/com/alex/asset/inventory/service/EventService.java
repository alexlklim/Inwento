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
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.dto.ProductV2Dto;
import com.alex.asset.product.mappers.ProductMapper;
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

    private final EventMapper eventMapper;
    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final InventoryRepo inventoryRepo;
    private final BranchRepo branchRepo;
    private final UnknownProductRepo unknownProductRepo;

    private final ProductService productService;
    private final ProductMapper productMapper;

    @SneakyThrows
    public EventV2Get getEvent(Long eventId) {
        log.info(TAG + "Get invent with id {}", eventId);
        EventV2Get dto = eventMapper.toDto(eventRepo.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event with id " + eventId + " not found")));
        dto.setUnknownProducts(getUnknownProductsForEvent(eventId));
        dto.setProducts(getAllProductsV2ForBranch(eventId));

        return dto;
    }


    @SneakyThrows
    public List<EventV2Get> getEventsForSpecificUserAndInvent(Long userId, Long inventId) {
        log.info(TAG + "Get event for user with id {} and invent with id {}", userId, inventId);
        return eventRepo.findAllByUserAndInventory(
                        userRepo.getUser(userId), inventoryRepo.findById(inventId).orElseThrow(
                                () -> new ResourceNotFoundException("Invent with id " + inventId + " not found")))
                .stream()
                .map(eventMapper::toDto)
                .toList();

    }

    @SneakyThrows
    public List<EventV2Get> getAllEventsForSpecificInvent(Long inventId) {
        log.info(TAG + "Get event for invent with id {}", inventId);
        return eventRepo.findAllByInventory(inventoryRepo.findById(inventId).orElseThrow(
                        () -> new ResourceNotFoundException("Invent with id " + inventId + " not found")))
                .stream()
                .map(eventMapper::toDto)
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
        event.setInventory(inventoryRepo.getCurrentInvent(LocalDate.now()).orElseThrow(
                () -> new ResourceNotFoundException("No active inventory at this time")));
        event.setUser(userRepo.getUser(userId));
        event.setBranch(branchRepo.findById(dto.getBranchId()).orElseThrow(
                () -> new ResourceNotFoundException("Branch with id " + dto.getBranchId() + " not found")));
        event.setInfo(dto.getInfo());
        event.setProducts(new ArrayList<>());
        return eventMapper.toDto(eventRepo.save(event));

    }


    @SneakyThrows
    public void updateVisibility(Long userId, DtoActive dto) {
        log.info(TAG + "Update visibility of event by user with id {} for event with id {}", userId, dto.getId());
        Event event = eventRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Event with this id not found"));
        event.setActive(dto.isActive());
        eventRepo.save(event);

    }


    @Modifying
    @SneakyThrows
    public void addProductsToEventByBarCode(Long userId, Long eventId, List<String> list) {
        log.info(TAG + "Add products to event by bar code by user with id {}", userId);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + eventId + " not found"));
        User user = userRepo.getUser(userId);
        if (event.getUser() != user)
            throw new UserIsNotOwnerOfEvent("User with id " + userId + " is not owner of event with id " + eventId);


        for (String barCode : list) {
            log.info(TAG + "Check product with bar code {}", barCode);
            Product product = productService.getByBarCode(barCode).orElse(null);
            // for product which exist in DB
            if (product != null) {
                log.info(TAG + "Product with bar code {} exists in DB", barCode);
                // if product is already added to this event
                if (!event.getProducts().contains(product)) {
                    log.info(TAG + "Product with bar code {} already added to products in this event", barCode);
                }
                if (product.getBranch() != event.getBranch()) {
                    log.info(TAG + "Product with bar code {} from another branch", barCode);
                    productService.productMovedToAnotherBranch(product, event.getBranch());
                }
                event.getProducts().add(product);
            } else {
                log.info(TAG + "Product with bar code {} doesnt exist in DB", barCode);
                // if product not exist in DB at all
                if (!unknownProductRepo.existsByCode(barCode)) {
                    log.info(TAG + "Product with bar code {} is completely new, add this product to unknown products", barCode);
                    UnknownProduct unknownProduct = new UnknownProduct();
                    unknownProduct.setCode(barCode);
                    unknownProduct.setEvent(event);
                    unknownProductRepo.save(unknownProduct);
                    return;
                } else {
                    log.info(TAG + "Product with bar code {} already added to unknown products", barCode);
                    if (unknownProductRepo.existsByCodeAndEvent(barCode, event))
                        log.warn(TAG + "Product with bar code {} was already scanned for this event", barCode);
                    else {
                        log.warn(TAG + "Product with bar code {} was scanned in another location", barCode);
                    }
                }
            }
        }

        eventRepo.save(event);


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

    private List<ProductV2Dto> getNotScannedProductForBranch(Branch branch) {
        log.info(TAG + "Get not scanned products by branch with id {}", branch.getId());
        return productService.getActiveProductsByBranch(branch)
                .stream()
                .filter(product -> !eventRepo.findProductsByBranch(branch).contains(product))
                .map(product -> productMapper.toProductV2Dto(product, InventoryStatus.NOT_SCANNED))
                .collect(Collectors.toList());
    }


    @SneakyThrows
    private List<ProductV2Dto> getScannedProductsForBranch(Branch branch) {
        log.info(TAG + "Get scanned products by branch with id {}", branch.getId());
        return eventRepo.findProductsByBranch(branch).stream()
                .map(product -> productMapper.toProductV2Dto(product, InventoryStatus.SCANNED))
                .collect(Collectors.toList());

    }


}
