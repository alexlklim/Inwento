package com.alex.inwento.managers;

import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.http.inventory.UnknownProductDTO;

import java.util.ArrayList;
import java.util.List;

public class FilterMng {


    public static String filteringData(String code, SettingsMng settingsMng) {
        int length = code.length();
        if ((settingsMng.getCodeLength() != 0 && length != settingsMng.getCodeLength()) ||
                (settingsMng.getCodeMinLength() != 0 && length < settingsMng.getCodeMinLength()) ||
                (settingsMng.getCodeMaxLength() != 0 && length > settingsMng.getCodeMaxLength())) {
            return null;
        }

        if (!settingsMng.getCodePrefix().isEmpty() && !code.startsWith(settingsMng.getCodePrefix()))
            return null;
        if (!settingsMng.getCodeSuffix().isEmpty() && !code.contains(settingsMng.getCodeSuffix()))
            return null;
        if (!settingsMng.getCodePostfix().isEmpty() && !code.endsWith(settingsMng.getCodePostfix()))
            return null;

        return code;
    }



    public static boolean isProductExistsByBarCode(List<ProductShortDTO> productList, String barcodeToFind) {
        for (ProductShortDTO product : productList) {
            if (product.getBarCode() != null && product.getBarCode().equalsIgnoreCase(barcodeToFind)) {
                return true;
            }
        }
        return false;
    }


    public static ProductShortDTO getProductById(List<ProductShortDTO> productList, int idToFind) {
        for (ProductShortDTO dto : productList) {
            if (dto.getId() == idToFind) {
                return dto;
            }
        }
        return null;
    }


    public static boolean isUnknownProductExistsByBarCode(List<UnknownProductDTO> productList, String codeToFind) {
        for (UnknownProductDTO product : productList) {
            if (product.getCode() != null && product.getCode().equalsIgnoreCase(codeToFind)) {
                return true;
            }
        }
        return false;
    }


    public static ProductShortDTO getProductByRFIDAndLocation(
            List<ProductShortDTO> products,
            String rfidCode,
            String location) {
        for (ProductShortDTO product : products) {
            if (product.getRfidCode() != null
                    && product.getLocation() != null
                    && product.getRfidCode().equalsIgnoreCase(rfidCode)
                    && product.getLocation().equalsIgnoreCase(location)) {
                return product;
            }
        }
        return null;
    }



    public static List<ProductShortDTO> filterProductsByTitle(List<ProductShortDTO> products, String searchedTitle) {
       if (searchedTitle.equalsIgnoreCase(""))
           return products;

        List<ProductShortDTO> filteredProducts = new ArrayList<>();
        if (searchedTitle.isEmpty()) {
            return filteredProducts;
        }

        for (ProductShortDTO product : products) {
            if (product.getTitle() != null && product.getTitle().toLowerCase().contains(searchedTitle.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

}
