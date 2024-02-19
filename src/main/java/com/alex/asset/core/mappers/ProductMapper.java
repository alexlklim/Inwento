package com.alex.asset.core.mappers;

import com.alex.asset.core.domain.Product;
import com.alex.asset.core.dto.ProductDto;

public class ProductMapper {

    public static Product toEntity(ProductDto dto){
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setInventoryNumber(dto.getInventoryNumber());
        product.setCode(dto.getCode());
        product.setReceiver(dto.getReceiver());
        product.setDocument(dto.getDocument());
        product.setDocumentDate(dto.getDocumentDate());
        product.setWarrantyPeriod(dto.getWarrantyPeriod());
        product.setInspectionDate(dto.getInspectionDate());
        product.setLastInventoryDate(dto.getLastInventoryDate());
        product.setLongitude(dto.getLocLongitude());
        product.setLatitude(dto.getLocLatitude());
        return product;
    }

    public static ProductDto toDto(Product entity){
        return null;
    }
}
