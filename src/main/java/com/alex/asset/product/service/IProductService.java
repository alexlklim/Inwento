package com.alex.asset.product.service;

import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;

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

    List<Map<String, Object>> getByValue(String keyWord, List<String> productFields);

    void update(Map<String, Object> updates, Long userId)
            throws ResourceNotFoundException;

    List<ProductHistoryDto> getHistoryOfProductById(Long productId)
            throws ResourceNotFoundException;

}