package com.alex.asset.core.mappers;

import com.alex.asset.core.domain.Product;
import com.alex.asset.core.domain.fields.*;
import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.alex.asset.core.dto.ProductDto;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductMapper {

    public static Product toEntity(ProductDto dto){
        Product product = new Product();
        product.setActive(true);
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        product.setBarCode(dto.getBarCode());
        product.setRfidCode(dto.getRfidCode());

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


        dto.setBarCode(entity.getBarCode());
        dto.setRfidCode(entity.getRfidCode());

        dto.setCreated(entity.getCreated().toLocalDate());
        dto.setUpdated(entity.getUpdated().toLocalDate());

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLiableUUID(entity.getLiable());
        dto.setReceiver(entity.getReceiver());

        setField(entity.getUnit(), dto::setUnit, Unit::getUnit);
        setField(entity.getAssetStatus(), dto::setAssetStatus, AssetStatus::getAssetStatus);
        setField(entity.getKst(), dto::setKst, KST::getKst);

        setField(entity.getType(), dto::setTypeName, Type::getType);
        setField(entity.getSubtype(), dto::setSubtypeName, Subtype::getSubtype);

        setField(entity.getBranch(), dto::setBranchName, Branch::getBranch);
        setField(entity.getProducer(), dto::setProducerName, Producer::getProducer);
        setField(entity.getSupplier(), dto::setSupplierName, Supplier::getSupplier);
        setField(entity.getMpk(), dto::setMpkName, MPK::getMpk);



        dto.setDocument(entity.getDocument());
        dto.setDocumentDate(entity.getDocumentDate());
        dto.setWarrantyPeriod(entity.getWarrantyPeriod());
        dto.setInspectionDate(entity.getInspectionDate());
        dto.setLastInventoryDate(entity.getLastInventoryDate());
        dto.setLocLongitude(entity.getLongitude());
        dto.setLocLatitude(entity.getLatitude());
        return dto;
    }




    private static <T, R> void setField(T object, Consumer<R> setter, Function<T, R> getter) {
        Optional.ofNullable(object).map(getter).ifPresent(setter);
    }
}
