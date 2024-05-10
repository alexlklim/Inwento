package com.alex.asset.inventory.mapper;

import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventMapper {


    private final UnknownProductRepo unknownProductRepo;
    private final ScannedProductRepo scannedProductRepo;
    private final ProductRepo productRepo;

    public Map<String, Object> toDTOWithCustomFields(Event event, List<String> fields) {
        Map<String, Object> dtoMap = new HashMap<>();
        dtoMap.put("id", event.getId());
        for (String field : fields) {
            switch (field) {
                case "active":
                    dtoMap.put("active", event.isActive());
                    break;
                case "info":
                    dtoMap.put("info", event.getInfo());
                    break;
                case "inventory_id":
                    dtoMap.put("inventory_id", event.getInventory().getId());
                    break;
                case "branch_id":
                    dtoMap.put("branch_id", event.getBranch().getId());
                    break;
                case "branch":
                    dtoMap.put("branch", event.getBranch().getBranch());
                    break;
                case "user_id":
                    dtoMap.put("user_id", event.getUser().getId());
                    break;
                case "user_email":
                    dtoMap.put("user_email", event.getUser().getEmail());
                    break;
                case "user_name":
                    dtoMap.put("user_name", event.getUser().getFirstname() + " " + event.getUser().getLastname());
                    break;
                case "unknown_products":
                    dtoMap.put("unknown_products", unknownProductRepo.findAllByEvent(event));
                    break;
                case "scanned_products":
                    List<ScannedProduct> scannedProductsList =
                            scannedProductRepo.findProductsByEventAndIsScanned(event, true);
                    dtoMap.put("scanned_products", getProductsMap(scannedProductsList));
                    break;
                case "not_scanned_products":
                    List<ScannedProduct> productsNotScannedDTOs =
                            scannedProductRepo.findProductsByEventAndIsScanned(event, false);
                    dtoMap.put("not_scanned_products", getProductsMap(productsNotScannedDTOs));
                    break;
                case "unknown_product_amount":
                    dtoMap.put("unknown_product_amount", unknownProductRepo.countProductsByEventId(event.getId()));
                    break;
                case "total_product_amount":
                    dtoMap.put("total_product_amount", productRepo.countProductsByBranchId(event.getBranch().getId()));
                    break;
                case "scanned_product_amount":
                    dtoMap.put("scanned_product_amount", scannedProductRepo.countByEventIdAndIsScanned(event, true));
                    break;
                default:
                    break;
            }

        }
        return dtoMap;
    }


    private List<Map<String, Object>> getProductsMap(List<ScannedProduct> scannedProductsList) {
        return scannedProductsList.stream()
                .map(scannedProduct -> {
                    Map<String, Object> productDTO = new HashMap<>();
                    productDTO.put("id", scannedProduct.getProduct().getId());
                    productDTO.put("title", scannedProduct.getProduct().getTitle());
                    productDTO.put("bar_code", scannedProduct.getProduct().getBarCode());
                    productDTO.put("rfid_code", scannedProduct.getProduct().getRfidCode());
                    productDTO.put("location", scannedProduct.getProduct().getLocation().getLocation());
                    return productDTO;
                })
                .collect(Collectors.toList());
    }
}
