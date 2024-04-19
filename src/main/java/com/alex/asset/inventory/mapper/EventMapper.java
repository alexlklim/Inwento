package com.alex.asset.inventory.mapper;

import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.inventory.repo.UnknownProductRepo;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
                    dtoMap.put("scanned_products", scannedProductRepo.findAllByEvent(event));
                    break;
                case "not_scanned_products":
                    List<Product> allProducts = productRepo.findAllByBranch(event.getBranch());
                    List<Product> scannedProducts = scannedProductRepo.findAllByEvent(event)
                            .stream()
                            .map(ScannedProduct::getProduct)
                            .toList();
                    List<Product> productsNotScanned = allProducts.stream()
                            .filter(product -> !scannedProducts.contains(product))
                            .toList();
                    dtoMap.put("not_scanned_products", productsNotScanned);
                    break;
                case "unknown_product_amount":
                    dtoMap.put("unknown_product_amount", unknownProductRepo.countProductsByEventId(event.getId()));
                    break;
                case "total_product_amount":
                    dtoMap.put("total_product_amount", productRepo.countProductsByBranchId(event.getBranch().getId()));
                    break;
                case "scanned_product_amount":
                    dtoMap.put("scanned_product_amount", scannedProductRepo.countScannedProductsByEventId(event.getId()));
                    break;
            }

        }
        return dtoMap;
    }

}
