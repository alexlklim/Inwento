package com.alex.asset.data.migration.service;

import com.alex.asset.product.domain.Product;

import java.util.List;

public class ProductComparator {


    public static boolean containsProductWithBarcode(List<Product> productList, String barcode) {
        return productList.stream()
                .anyMatch(product -> barcode.equals(product.getBarCode()));
    }

    public static boolean containsProductWithRfidCode(List<Product> productList, String rfidCode) {
        return productList.stream()
                .anyMatch(product -> rfidCode.equals(product.getRfidCode()));
    }

    public static boolean containsProductWithInventoryNumber(List<Product> productList, String inventoryNumber) {
        return productList.stream()
                .anyMatch(product -> inventoryNumber.equals(product.getInventoryNumber()));
    }

    public static boolean containsProductWithSerialNumber(List<Product> productList, String serialNumber) {
        return productList.stream()
                .anyMatch(product -> serialNumber.equals(product.getSerialNumber()));
    }
}
