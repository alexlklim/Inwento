package com.alex.inwento.managers;

import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.http.inventory.UnknownProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FilterMng {


    public static String filteringData(String code, SettingsMng settingsMng) {
        int length = code.length();
        if ((settingsMng.getCodeLength() != 0 && length != settingsMng.getCodeLength()) ||
                (settingsMng.getCodeMinLength() != 0 && length < settingsMng.getCodeMinLength()) ||
                (settingsMng.getCodeMaxLength() != 0 && length > settingsMng.getCodeMaxLength())) {
            return null;
        }

        if (!settingsMng.getCodePrefix().equals("") && !code.startsWith(settingsMng.getCodePrefix()))
            return null;
        if (!settingsMng.getCodeSuffix().equals("") && !code.contains(settingsMng.getCodeSuffix()))
            return null;
        if (!settingsMng.getCodePostfix().equals("") && !code.endsWith(settingsMng.getCodePostfix()))
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
}
