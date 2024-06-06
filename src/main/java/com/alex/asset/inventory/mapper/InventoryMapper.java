package com.alex.asset.inventory.mapper;


import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.utils.dictionaries.UtilEvent;
import com.alex.asset.utils.dictionaries.UtilsInventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InventoryMapper {
    private final String TAG = "INVENTORY_MAPPER - ";
    private final ProductRepo productRepo;
    private final EventRepo eventRepo;
    private final UnknownProductRepo unknownProductRepo;
    private final ScannedProductRepo scannedProductRepo;
    private final EventMapper eventMapper;
    public Map<String, Object> toDTOWithCustomFields(
            Inventory inventory,
            List<String> fields
    ) {
        Map<String, Object> dtoMap = new HashMap<>();
        Map<String, Supplier<Object>> df = new HashMap<>();
        df.put(UtilsInventory.INFO, () -> getInfoAboutInventory(inventory));
        df.put(UtilsInventory.PRODUCTS_AMOUNT, () -> getProductsAmount(eventRepo.getActiveEventsByInventory(inventory)));
        df.put(UtilsInventory.EVENTS, () -> getEvents(eventRepo.getActiveEventsByInventory(inventory)));
        fields.forEach(field -> dtoMap.put(field, df.getOrDefault(field, () -> "").get()));
        return dtoMap;
    }


    private Map<String, Object> getInfoAboutInventory(Inventory inventory) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilsInventory.ID, inventory.getId());
        map.put(UtilsInventory.IS_ACTIVE, inventory.isActive());
        map.put(UtilsInventory.START_DATE, inventory.getStartDate());
        map.put(UtilsInventory.FINISH_DATE, inventory.getFinishDate());
        map.put(UtilsInventory.IS_FINISHED, inventory.isFinished());
        return map;
    }


    private Map<String, Object> getProductsAmount(List<Event> events) {
        Map<String, Object> map = new HashMap<>();

        int unknownProductAmount = events.stream()
                .mapToInt(event -> unknownProductRepo.countProductsByEventId(event.getId()))
                .sum();
        map.put(UtilsInventory.UNKNOWN_PRODUCT_AMOUNT, unknownProductAmount);

        int scannedProductAmount = events.stream()
                .mapToInt(event -> scannedProductRepo.countByEventIdAndIsScanned(event, true))
                .sum();
        map.put(UtilsInventory.SCANNED_PRODUCT_AMOUNT, scannedProductAmount);

        int totalProductAmount = events.stream()
                .mapToInt(event -> productRepo.countProductsByBranchId(event.getBranch().getId()))
                .sum();
        map.put(UtilsInventory.TOTAL_PRODUCT_AMOUNT, totalProductAmount);

        int numberOfEvents = events.size();
        map.put(UtilsInventory.NUMBERS_OF_EVENTS, numberOfEvents);
        return map;
    }



    private List<Map<String, Object>> getEvents(List<Event> events) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Event event : events) {
            list.add(eventMapper.toDTOWithCustomFields(event, UtilEvent.getFieldsSimpleView()));
        }
        return list;
    }



    public List<Map<String, Object>> toDTOsWithCustomFields(List<Inventory> inventories, List<String> fields) {
        return inventories.stream()
                .map(inventory -> toDTOWithCustomFields(inventory, fields))
                .toList();
    }

}
