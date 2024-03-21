package com.alex.asset.product.mappers;

import com.alex.asset.inventory.domain.event.InventoryStatus;
import com.alex.asset.product.dto.ProductV2Dto;

import java.util.List;

public class ProductUtils {

    public static int countScannedProducts(List<ProductV2Dto> productList) {
        int scannedCount = 0;
        for (ProductV2Dto product : productList) {
            if (product.getInventoryStatus() == InventoryStatus.SCANNED) {
                scannedCount++;
            }
        }
        return scannedCount;
    }


    public static int countNotScannedProducts(List<ProductV2Dto> productList) {
        int scannedCount = 0;
        for (ProductV2Dto product : productList) {
            if (product.getInventoryStatus() == InventoryStatus.NOT_SCANNED) {
                scannedCount++;
            }
        }
        return scannedCount;
    }
}
