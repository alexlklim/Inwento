package com.alex.asset.product.mappers;

import com.alex.asset.product.domain.Product;
import com.alex.asset.utils.dictionaries.UtilProduct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


@Service
public class ProductMapper {

    public static Map<String, Object> toDTOWithCustomFields(Product product, List<String> fields) {
        Map<String, Object> dtoMap = new HashMap<>();
        Map<String, Supplier<Object>> dataFetchers = new HashMap<>();


        dataFetchers.put(UtilProduct.ID, product::getId);
        dataFetchers.put(UtilProduct.ACTIVE, product::isActive);
        dataFetchers.put(UtilProduct.TITLE, product::getTitle);
        dataFetchers.put(UtilProduct.DESCRIPTION, () -> product.getDescription() != null ? product.getDescription() : "");
        dataFetchers.put(UtilProduct.PRICE, () -> product.getPrice() != null ? product.getPrice() : "");
        dataFetchers.put(UtilProduct.BAR_CODE, product::getBarCode);
        dataFetchers.put(UtilProduct.RFID_CODE, product::getRfidCode);
        dataFetchers.put(UtilProduct.INVENTORY_NUMBER, product::getInventoryNumber);
        dataFetchers.put(UtilProduct.SERIAL_NUMBER, product::getSerialNumber);
        dataFetchers.put(UtilProduct.LIABLE_ID,
                () -> product.getLiable() != null ? product.getLiable().getId() : "");
        dataFetchers.put(UtilProduct.LIABLE_NAME,
                () -> (product.getLiable() != null
                        && product.getLiable().getFirstname() != null
                        && product.getLiable().getLastname() != null) ?
                product.getLiable().getFirstname() + " " + product.getLiable().getLastname() : "");
        dataFetchers.put(UtilProduct.RECEIVER, () -> product.getReceiver() != null ? product.getReceiver() : "");
        dataFetchers.put(UtilProduct.KST_ID,
                () -> product.getKst() != null ? product.getKst().getId() : "");
        dataFetchers.put(UtilProduct.ASSET_STATUS_ID,
                () -> product.getAssetStatus() != null ? product.getAssetStatus().getId() : "");
        dataFetchers.put(UtilProduct.UNIT_ID,
                () -> product.getUnit() != null ? product.getUnit().getId() : "");
        dataFetchers.put(UtilProduct.BRANCH_ID,
                () -> product.getBranch() != null ? product.getBranch().getId() : "");
        dataFetchers.put(UtilProduct.LOCATION_ID,
                () -> product.getLocation() != null ? product.getLocation().getId() : "");
        dataFetchers.put(UtilProduct.MPK_ID,
                () -> product.getMpk() != null ? product.getMpk().getId() : "");
        dataFetchers.put(UtilProduct.TYPE_ID,
                () -> product.getType() != null ? product.getType().getId() : "");
        dataFetchers.put(UtilProduct.SUBTYPE_ID,
                () -> product.getSubtype() != null ? product.getSubtype().getId() : "");
        dataFetchers.put(UtilProduct.KST,
                () -> product.getKst() != null ? product.getKst().getKst() : "");
        dataFetchers.put(UtilProduct.ASSET_STATUS,
                () -> product.getAssetStatus() != null ? product.getAssetStatus().getAssetStatus() : "");
        dataFetchers.put(UtilProduct.UNIT,
                () -> product.getUnit() != null ? product.getUnit().getUnit() : "");
        dataFetchers.put(UtilProduct.BRANCH,
                () -> product.getBranch() != null ? product.getBranch().getBranch() : "");
        dataFetchers.put(UtilProduct.LOCATION,
                () -> product.getLocation() != null ? product.getLocation().getLocation() : "");
        dataFetchers.put(UtilProduct.MPK,
                () -> product.getMpk() != null ? product.getMpk().getMpk() : "");
        dataFetchers.put(UtilProduct.TYPE,
                () -> product.getType() != null ? product.getType().getType() : "");
        dataFetchers.put(UtilProduct.SUBTYPE,
                () -> product.getSubtype() != null ? product.getSubtype().getSubtype() : "");
        dataFetchers.put(UtilProduct.PRODUCER, () -> product.getProducer() != null ? product.getProducer() : "");
        dataFetchers.put(UtilProduct.SUPPLIER,() -> product.getSupplier() != null ? product.getSupplier() : "");
        dataFetchers.put(UtilProduct.SCRAPPING, product::isScrapping);
        dataFetchers.put(UtilProduct.SCRAPPING_DATE,
                () -> product.getScrappingDate() != null ? product.getScrappingDate() : "");
        dataFetchers.put(UtilProduct.SCRAPPING_REASON,
                () -> product.getScrappingReason() != null ? product.getScrappingReason() : "");
        dataFetchers.put(UtilProduct.DOCUMENT,
                () -> product.getDocument() != null ? product.getDocument() : "");
        dataFetchers.put(UtilProduct.DOCUMENT_DATE,
                () -> product.getDocumentDate() != null ? product.getDocumentDate() : "");
        dataFetchers.put(UtilProduct.WARRANTY_PERIOD,
                () -> product.getWarrantyPeriod() != null ? product.getWarrantyPeriod() : "");
        dataFetchers.put(UtilProduct.INSPECTION_DATE,
                () -> product.getInspectionDate() != null ? product.getInspectionDate() : "");
        dataFetchers.put(UtilProduct.LONGITUDE,
                () -> product.getLongitude() != null ? product.getLongitude() : "");
        dataFetchers.put(UtilProduct.LATITUDE,
                () -> product.getLatitude() != null ? product.getLatitude() : "");
        dataFetchers.put(UtilProduct.COMMENTS,
                () -> product.getComments() != null ? CommentMapper.toDTOs(product.getComments()) : "");
        dataFetchers.put(UtilProduct.HISTORY,
                () -> product.getProductHistories() != null ?
                        ProductHistoryMapper.toDTOs(product.getProductHistories()) : "");

        fields.forEach(field -> dtoMap.put(field, dataFetchers.getOrDefault(field, () -> "").get()));
        return dtoMap;
    }


    public static List<Map<String, Object>> toDTOsWithCustomFields(List<Product> products, List<String> productFields) {
        return products.stream().map(product -> toDTOWithCustomFields(product, productFields)).toList();
    }
}
