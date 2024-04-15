package com.alex.asset.data.migration.service;

import com.alex.asset.data.migration.dto.Asset;
import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;

import java.util.Objects;


public class AssetMapper {


    public static Product toProduct(Asset asset, User createdBy, User liable) {
        Product product = new Product();

        product.setTitle(asset.getTitle());
        product.setDescription(Objects.equals(asset.getDescription(), "0.0") ? null : asset.getDescription());
        product.setPrice(Objects.equals(asset.getPrice(), "0.0") ? null : asset.getPrice());
        product.setBarCode(Objects.equals(asset.getBarCode(), "0.0") ? null : asset.getBarCode());
        product.setRfidCode(Objects.equals(asset.getRfidCode(), "0.0") ? null : asset.getRfidCode());
        product.setInventoryNumber(Objects.equals(asset.getInventoryNumber(), "0.0") ? null : asset.getInventoryNumber());
        product.setSerialNumber(Objects.equals(asset.getSerialNumber(), "0.0") ? null : asset.getSerialNumber());
        product.setCreatedBy(createdBy);
        product.setLiable(liable);
        product.setCreatedBy(createdBy);
        product.setReceiver(Objects.equals(asset.getReceiver(), "0.0") ? null : asset.getReceiver());
        product.setKst(asset.getKst());
        product.setAssetStatus(asset.getAssetStatus());
        product.setUnit(asset.getUnit());
        product.setBranch(asset.getBranch());
        product.setLocation(asset.getLocation());
        product.setMpk(asset.getMpk());
        product.setType(asset.getType());
        product.setSubtype(asset.getSubtype());
        product.setProducer(Objects.equals(asset.getProducer(), "0.0") ? null : asset.getProducer());
        product.setSupplier(Objects.equals(asset.getSupplier(), "0.0") ? null : asset.getSupplier());
        product.setDocument(Objects.equals(asset.getDocument(), "0.0") ? null : asset.getDocument());
        product.setDocumentDate(Objects.equals(asset.getDocumentDate(), "0.0") ? null : asset.getDocumentDate());
        product.setWarrantyPeriod(asset.getWarrantyPeriod());
        product.setInspectionDate(asset.getInspectionDate());

        product.setActive(true);
        product.setScrapping(false);

        return product;
    }
}
