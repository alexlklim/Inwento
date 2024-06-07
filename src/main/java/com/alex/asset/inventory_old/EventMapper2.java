package com.alex.asset.inventory_old;

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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventMapper2 {



    private final UnknownProductRepo unknownProductRepo;
    private final ScannedProductRepo scannedProductRepo;
    private final ProductRepo productRepo;

    public Map<String, Object> toDTOWithCustomFields(Event event, List<String> fields) {
        Map<String, Object> dtoMap = new HashMap<>();
        dtoMap.put(UtilEvent.ID, event.getId());
        for (String field : fields) {
            switch (field) {
                case UtilEvent.ACTIVE: dtoMap.put(UtilEvent.ACTIVE, event.isActive()); break;
                case UtilEvent.INFO: dtoMap.put(UtilEvent.INFO, event.getInfo()); break;
                case UtilEvent.INVENTORY_ID: dtoMap.put(UtilEvent.INVENTORY_ID, event.getInventory().getId()); break;
                case UtilEvent.BRANCH_ID: dtoMap.put(UtilEvent.BRANCH_ID, event.getBranch().getId()); break;
                case UtilEvent.BRANCH: dtoMap.put(UtilEvent.BRANCH, event.getBranch().getBranch()); break;
                case UtilEvent.USER_ID: dtoMap.put(UtilEvent.USER_ID, event.getUser().getId()); break;
                case UtilEvent.USER_EMAIL: dtoMap.put(UtilEvent.USER_EMAIL, event.getUser().getEmail()); break;

                case UtilEvent.USER_NAME:
                    dtoMap.put(UtilEvent.USER_NAME, event.getUser().getFirstname() + " " + event.getUser().getLastname());
                    break;
                case UtilEvent.UNKNOWN_PRODUCTS:
                    dtoMap.put(UtilEvent.UNKNOWN_PRODUCTS, unknownProductRepo.findAllByEvent(event));
                    break;
                case UtilEvent.SCANNED_PRODUCTS:
                    List<ScannedProduct> scannedProductsList =
                            scannedProductRepo.findProductsByEventAndIsScanned(event, true);
                    dtoMap.put(UtilEvent.SCANNED_PRODUCTS, getProductsMap(scannedProductsList));
                    break;
                case UtilEvent.NOT_SCANNED_PRODUCTS:
                    List<ScannedProduct> productsNotScannedDTOs =
                            scannedProductRepo.findProductsByEventAndIsScanned(event, false);
                    dtoMap.put(UtilEvent.NOT_SCANNED_PRODUCTS, getProductsMap(productsNotScannedDTOs));
                    break;
                case UtilEvent.UNKNOWN_PRODUCT_AMOUNT:
                    dtoMap.put(UtilEvent.UNKNOWN_PRODUCT_AMOUNT, unknownProductRepo.countProductsByEventId(event.getId()));
                    break;
                case UtilEvent.TOTAL_PRODUCT_AMOUNT:
                    dtoMap.put(UtilEvent.TOTAL_PRODUCT_AMOUNT, productRepo.countProductsByBranchId(event.getBranch().getId()));
                    break;
                case UtilEvent.SCANNED_PRODUCT_AMOUNT:
                    dtoMap.put(UtilEvent.SCANNED_PRODUCT_AMOUNT, scannedProductRepo.countByEventIdAndIsScanned(event, true));
                    break;
                default:
                    break;
            }

        }
        return dtoMap;
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
