package com.alex.asset.inventory.mapper;

import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.utils.dictionaries.UtilEvent;
import com.alex.asset.utils.dictionaries.UtilProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventMapper {
    private final UnknownProductRepo unknownProductRepo;
    private final ScannedProductRepo scannedProductRepo;
    private final ProductRepo productRepo;

    public List<Map<String, Object>> toDTOsWithCustomFields(List<Event> events, List<String> fields) {
    return events.stream().map(event -> toDTOWithCustomFields(event, fields)).toList();
    }
    public Map<String, Object> toDTOWithCustomFields(Event event, List<String> fields) {
        Map<String, Object> dtoMap = new HashMap<>();
        Map<String, Supplier<Object>> df = new HashMap<>();
        df.put(UtilEvent.INFO, () -> getInfo(event));
        df.put(UtilEvent.USER, () -> getUserInfo(event));
        df.put(UtilEvent.UNKNOWN_PRODUCTS, () -> unknownProductRepo.findAllByEvent(event));
        df.put(UtilEvent.SCANNED_PRODUCTS, () -> getProductsMap(scannedProductRepo.findProductsByEventAndIsScanned(event, true)));
        df.put(UtilEvent.NOT_SCANNED_PRODUCTS, () -> getProductsMap(scannedProductRepo.findProductsByEventAndIsScanned(event, false)));
        df.put(UtilEvent.PRODUCT_AMOUNT, () -> getProductAmount(event));
        fields.forEach(field -> dtoMap.put(field, df.getOrDefault(field, () -> "").get()));
        dtoMap.put(UtilEvent.ID, event.getId());
        return dtoMap;
    }

    private Map<String, Object> getProductAmount(Event event) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilEvent.UNKNOWN_PRODUCT_AMOUNT, unknownProductRepo.countProductsByEventId(event.getId()));
        map.put(UtilEvent.TOTAL_PRODUCT_AMOUNT, productRepo.countProductsByBranchId(event.getBranch().getId()));
        map.put(UtilEvent.SCANNED_PRODUCT_AMOUNT, scannedProductRepo.countByEventIdAndIsScanned(event, true));
        return map;
    }

    private Map<String, Object> getUserInfo(Event event) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilEvent.USER_ID, event.getUser().getId());
        map.put(UtilEvent.USER_EMAIL, event.getUser().getEmail());
        map.put(UtilEvent.USER_NAME, event.getUser().getFirstname() + " " + event.getUser().getLastname());
        return map;
    }

    private Map<String, Object> getInfo(Event event) {
        Map<String, Object> map = new HashMap<>();
        map.put(UtilEvent.ACTIVE, event.isActive());
        map.put(UtilEvent.INFO, event.getInfo());
        map.put(UtilEvent.INVENTORY_ID, event.getInventory().getId());
        map.put(UtilEvent.BRANCH_ID, event.getBranch().getId());
        map.put(UtilEvent.BRANCH, event.getBranch().getBranch());
        return map;
    }


    List<Map<String, Object>> getProductsMap(List<ScannedProduct> scannedProductsList) {
        return scannedProductsList.stream()
                .map(scannedProduct -> {
                    Map<String, Object> productDTO = new HashMap<>();
                    productDTO.put(UtilProduct.ID, scannedProduct.getProduct().getId());
                    productDTO.put(UtilProduct.TITLE, scannedProduct.getProduct().getTitle());
                    productDTO.put(UtilProduct.BAR_CODE, scannedProduct.getProduct().getBarCode());
                    productDTO.put(UtilProduct.RFID_CODE, scannedProduct.getProduct().getRfidCode());
                    productDTO.put(UtilProduct.LOCATION, scannedProduct.getProduct().getLocation().getLocation());
                    productDTO.put(UtilProduct.BRANCH, scannedProduct.getProduct().getBranch().getBranch());
                    productDTO.put(UtilProduct.PRODUCER, scannedProduct.getProduct().getProducer() != null
                            ? scannedProduct.getProduct().getProducer() : "");
                    return productDTO;
                })
                .collect(Collectors.toList());
    }
}
