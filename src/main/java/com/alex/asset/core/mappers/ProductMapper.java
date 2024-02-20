package com.alex.asset.core.mappers;

import com.alex.asset.core.domain.Product;
import com.alex.asset.core.dto.ProductDto;

public class ProductMapper {

    public static Product toEntity(ProductDto dto){
        Product product = new Product();
        product.setActive(true);
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        product.setInventoryNumber(dto.getInventoryNumber());
        product.setCode(dto.getCode());

        product.setLiable(dto.getLiableUUID());
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
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());


        dto.setInventoryNumber(entity.getInventoryNumber());
        dto.setCode(entity.getCode());

        dto.setCreated(entity.getCreated().toLocalDate());
        dto.setUpdated(entity.getUpdated().toLocalDate());

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLiableUUID(entity.getLiable());
        dto.setReceiver(entity.getReceiver());

        dto.setUnit(entity.getUnit().getUnit());
        dto.setAssetStatus(entity.getAssetStatus().getAssetStatus());
        dto.setKst(entity.getKst().getKst());

        dto.setTypeName(entity.getType().getType());
        dto.setSubtypeName(entity.getSubtype().getSubtype());

        dto.setBranchName(entity.getBranch().getBranch());
        dto.setProducerName(entity.getProducer().getProducer());
        dto.setSupplierName(entity.getSupplier().getSupplier());
        dto.setMpkName(entity.getMpk().getMpk());

        dto.setDocument(entity.getDocument());
        dto.setDocumentDate(entity.getDocumentDate());
        dto.setWarrantyPeriod(entity.getWarrantyPeriod());
        dto.setInspectionDate(entity.getInspectionDate());
        dto.setLastInventoryDate(entity.getLastInventoryDate());
        dto.setLocLongitude(entity.getLongitude());
        dto.setLocLatitude(entity.getLatitude());
        return dto;
    }
}
