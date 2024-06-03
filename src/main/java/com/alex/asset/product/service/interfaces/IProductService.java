package com.alex.asset.product.service.interfaces;

import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.product.dto.ProductCodesDTO;

import java.util.List;
import java.util.Map;

public interface IProductService {

    Map<String, Object> getById(List<String> productFields, Long productId, Long userId)
            throws ResourceNotFoundException;

    List<Map<String, Object>> getAllProducts(String mode, Boolean isScrap, List<String> productFields, Long userId)
            throws ResourceNotFoundException;

    Map<String, Object> getByUniqueValues(String barCode, String rfidCode, String inventoryNumber, String serialNumber,
                                          List<String> productFields, Long userId)
            throws ResourceNotFoundException;

    List<Map<String, Object>> getByValue(String keyWord, Boolean isScrapped, List<String> productFields);

    ProductCodesDTO update(Map<String, Object> updates, Long userId)
            throws ResourceNotFoundException;


    List<Map<String, Object>> getByWarrantyPeriod(String startDate, String endDate);

    List<Map<String, Object>> getByInspectionPeriod(String startDate, String endDate);

}