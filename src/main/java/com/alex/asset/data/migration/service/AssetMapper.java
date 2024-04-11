package com.alex.asset.data.migration.service;

import com.alex.asset.data.migration.dto.Asset;
import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;

public class AssetMapper {


    public static Product toProduct(Asset asset, User createdBy) {
        Product product = new Product();

        product.setTitle(asset.getTitle());
        product.setDescription(asset.getDescription());
        product.setPrice(asset.getPrice());
        product.setBarCode(asset.getBarCode());
        product.setRfidCode(asset.getRfidCode());
        product.setInventoryNumber(asset.getInventoryNumber());
        product.setSerialNumber(asset.getSerialNumber());
        product.setCreatedBy(createdBy);
        product.setLiable(asset.getLiable());
        product.setCreatedBy(asset.getCreatedBy());
        product.setReceiver(asset.getReceiver());
        product.setKst(asset.getKst());
        product.setAssetStatus(asset.getAssetStatus());
        product.setUnit(asset.getUnit());
        product.setBranch(asset.getBranch());
        product.setMpk(asset.getMpk());
        product.setType(asset.getType());
        product.setSubtype(asset.getSubtype());
        product.setProducer(asset.getProducer());
        product.setSupplier(asset.getSupplier());
        product.setDocument(asset.getDocument());
        product.setDocumentDate(asset.getDocumentDate());
        product.setWarrantyPeriod(asset.getWarrantyPeriod());
        product.setInspectionDate(asset.getInspectionDate());

        product.setActive(true);
        product.setScrapping(false);

        return product;
    }
}
