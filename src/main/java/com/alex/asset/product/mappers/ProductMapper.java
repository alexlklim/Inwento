package com.alex.asset.product.mappers;

import com.alex.asset.product.domain.Product;
import com.alex.asset.utils.UtilProduct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductMapper {

    public static Map<String, Object> toDTOWithCustomFields(Product product, List<String> fields) {
        Map<String, Object> dtoMap = new HashMap<>();
        dtoMap.put(UtilProduct.ID, product.getId());
        dtoMap.put(UtilProduct.ACTIVE, product.isActive());
        dtoMap.put(UtilProduct.TITLE, product.getTitle());
        for (String field : fields) {
            switch (field) {
                case UtilProduct.DESCRIPTION:
                    dtoMap.put(UtilProduct.TITLE, product.getDescription()!= null ? product.getDescription() : "");
                    break;
                case UtilProduct.PRICE:
                    dtoMap.put(UtilProduct.PRICE, product.getPrice()!= null ? product.getPrice() : "");
                    break;
                case UtilProduct.BAR_CODE:
                    dtoMap.put(UtilProduct.BAR_CODE, product.getBarCode()!= null ? product.getBarCode() : "");
                    break;
                case UtilProduct.RFID_CODE:
                    dtoMap.put(UtilProduct.RFID_CODE, product.getRfidCode()!= null ? product.getRfidCode() : "");
                    break;
                case UtilProduct.INVENTORY_NUMBER:
                    dtoMap.put(UtilProduct.INVENTORY_NUMBER, product.getInventoryNumber()!= null ? product.getInventoryNumber() : "");
                    break;
                case UtilProduct.SERIAL_NUMBER:
                    dtoMap.put(UtilProduct.SERIAL_NUMBER, product.getSerialNumber()!= null ? product.getSerialNumber() : "");
                    break;
                case UtilProduct.LIABLE_ID:
                    dtoMap.put(UtilProduct.LIABLE_ID, product.getLiable() != null ? product.getLiable().getId() : "");
                    break;
                case UtilProduct.LIABLE_NAME:
                    dtoMap.put(
                            UtilProduct.LIABLE_NAME,
                            (product.getLiable() != null && product.getLiable().getFirstname() != null && product.getLiable().getLastname() != null) ?
                                    product.getLiable().getFirstname() + " " + product.getLiable().getLastname() : "");
                    break;


                case UtilProduct.RECEIVER:
                    dtoMap.put(UtilProduct.RECEIVER, product.getReceiver()!= null ? product.getReceiver() : "");
                    break;
                case UtilProduct.KST_ID:
                    dtoMap.put(UtilProduct.KST_ID, product.getKst() != null ? product.getKst().getId() : "");
                    break;
                case UtilProduct.ASSET_STATUS_ID:
                    dtoMap.put(UtilProduct.ASSET_STATUS_ID, product.getAssetStatus() != null ? product.getAssetStatus().getId() : "");
                    break;
                case UtilProduct.UNIT_ID:
                    dtoMap.put(UtilProduct.UNIT_ID, product.getUnit() != null ? product.getUnit().getId() : "");
                    break;
                case UtilProduct.BRANCH_ID:
                    dtoMap.put(UtilProduct.BRANCH_ID, product.getBranch() != null ? product.getBranch().getId() : "");
                    break;
                case UtilProduct.LOCATION_ID:
                    dtoMap.put(UtilProduct.LOCATION_ID, product.getLocation() != null ? product.getLocation().getId() : "");
                    break;
                case UtilProduct.MPK_ID:
                    dtoMap.put(UtilProduct.MPK_ID, product.getMpk() != null ? product.getMpk().getId() : "");
                    break;
                case UtilProduct.TYPE_ID:
                    dtoMap.put(UtilProduct.TYPE_ID, product.getType() != null ? product.getType().getId() : "");
                    break;
                case UtilProduct.SUBTYPE_ID:
                    dtoMap.put(UtilProduct.SUBTYPE_ID, product.getSubtype() != null ? product.getSubtype().getId() : "");
                    break;

                case UtilProduct.KST:
                    dtoMap.put(UtilProduct.KST, product.getKst() != null ? product.getKst().getKst() : "");
                    break;
                case UtilProduct.ASSET_STATUS:
                    dtoMap.put(UtilProduct.ASSET_STATUS, product.getAssetStatus() != null ? product.getAssetStatus().getAssetStatus() : "");
                    break;
                case UtilProduct.UNIT:
                    dtoMap.put(UtilProduct.UNIT, product.getUnit() != null ? product.getUnit().getUnit() : "");
                    break;
                case UtilProduct.BRANCH:
                    dtoMap.put(UtilProduct.BRANCH, product.getBranch() != null ? product.getBranch().getBranch() : "");
                    break;
                case UtilProduct.LOCATION:
                    dtoMap.put(UtilProduct.LOCATION, product.getLocation() != null ? product.getLocation().getLocation() : "");
                    break;
                case UtilProduct.MPK:
                    dtoMap.put(UtilProduct.MPK, product.getMpk() != null ? product.getMpk().getMpk() : "");
                    break;
                case UtilProduct.TYPE:
                    dtoMap.put(UtilProduct.TYPE, product.getType() != null ? product.getType().getType() : "");
                    break;
                case UtilProduct.SUBTYPE:
                    dtoMap.put(UtilProduct.SUBTYPE, product.getSubtype() != null ? product.getSubtype().getSubtype() : "");
                    break;
                case UtilProduct.PRODUCER:
                    dtoMap.put(UtilProduct.PRODUCER, product.getProducer()!= null ? product.getProducer() : "");
                    break;
                case UtilProduct.SUPPLIER:
                    dtoMap.put(UtilProduct.SUPPLIER, product.getSupplier() != null ? product.getSupplier() : "");
                    break;
                case UtilProduct.SCRAPPING:
                    dtoMap.put(UtilProduct.SCRAPPING, product.isScrapping());
                    break;
                case UtilProduct.SCRAPPING_DATE:
                    dtoMap.put(UtilProduct.SCRAPPING_DATE, product.getScrappingDate()!= null ? product.getScrappingDate() : "");
                    break;
                case UtilProduct.SCRAPPING_REASON:
                    dtoMap.put(UtilProduct.SCRAPPING_REASON, product.getScrappingReason()!= null ? product.getScrappingReason() : "");
                    break;
                case UtilProduct.DOCUMENT:
                    dtoMap.put(UtilProduct.DOCUMENT, product.getDocument()!= null ? product.getDocument() : "");
                    break;
                case UtilProduct.DOCUMENT_DATE:
                    dtoMap.put(UtilProduct.DOCUMENT_DATE, product.getDocumentDate()!= null ? product.getDocumentDate() : "");
                    break;
                case UtilProduct.WARRANTY_PERIOD:
                    dtoMap.put(UtilProduct.WARRANTY_PERIOD, product.getWarrantyPeriod()!= null ? product.getWarrantyPeriod() : "");
                    break;
                case UtilProduct.INSPECTION_DATE:
                    dtoMap.put(UtilProduct.INSPECTION_DATE, product.getInspectionDate()!= null ? product.getInspectionDate() : "");
                    break;
                case UtilProduct.LONGITUDE:
                    dtoMap.put(UtilProduct.LONGITUDE, product.getLongitude()!= null ? product.getLongitude() : "");
                    break;
                case UtilProduct.LATITUDE:
                    dtoMap.put(UtilProduct.LATITUDE, product.getLatitude()!= null ? product.getLatitude() : "");
                    break;
                case UtilProduct.COMMENTS:
                    dtoMap.put(UtilProduct.COMMENTS, product.getComments() != null ? CommentMapper.toDTOs(product.getComments()) : "");
                    break;
                case UtilProduct.HISTORY:
                    dtoMap.put(UtilProduct.HISTORY, product.getProductHistories() != null ? ProductHistoryMapper.toDTOs(product.getProductHistories()) : "");
                    break;
                default:
                    break;
            }
        }
        return dtoMap;
    }
}
