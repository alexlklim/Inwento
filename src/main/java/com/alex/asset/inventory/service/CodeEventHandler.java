package com.alex.asset.inventory.service;


import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.configure.repo.LocationRepo;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.inventory.domain.event.CodeType;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.inventory.domain.event.UnknownProduct;
import com.alex.asset.inventory.mapper.EventMapper;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.security.repo.UserRepo;
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
public class CodeEventHandler {

    private final String TAG = "CODE_EVENT_HANDLER - ";
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


    public void addProductsToEventByRfidCode(List<String> listOfCodes, Long eventId, Long userId) {
        log.info(TAG + "Adding products to event by rfid code by user with id {}", userId);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        for (String rfidCode : listOfCodes) {
            Product product = productRepo.getByRfidCode(rfidCode).orElse(null);
            if (product == null) {
                log.error(TAG + "PRODUCT IS NULL");
                continue;
            }
            ScannedProduct scannedProduct = scannedProductRepo.findByProductAndEvent(product, event).orElse(null);
            if (scannedProduct == null) {
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
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + eventId + " not found"));
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
            productRepo.save(product);
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

}
