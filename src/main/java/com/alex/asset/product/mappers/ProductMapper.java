package com.alex.asset.product.mappers;

import com.alex.asset.comments.CommentMapper;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.product.dto.ProductHistoryDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductMapper {
    public static ProductHistoryDto toProductHistoryDto(ProductHistory productHistory) {
        return new ProductHistoryDto().toBuilder()
                .created(productHistory.getCreated())
                .username(productHistory.getUser().getFirstname() + productHistory.getUser().getLastname())
                .email(productHistory.getUser().getEmail())
                .activity(productHistory.getActivity())
                .build();
    }

    public static Map<String, Object> toDTOWithCustomFields(Product product, List<String> fields) {
        Map<String, Object> dtoMap = new HashMap<>();
        dtoMap.put("id", product.getId());
        dtoMap.put("active", product.isActive());
        dtoMap.put("title", product.getTitle());
        for (String field : fields) {
            switch (field) {
                case "description":
                    dtoMap.put("description", product.getDescription()!= null ? product.getDescription() : "");
                    break;
                case "price":
                    dtoMap.put("price", product.getPrice()!= null ? product.getPrice() : "");
                    break;
                case "bar_code":
                    dtoMap.put("bar_code", product.getBarCode()!= null ? product.getBarCode() : "");
                    break;
                case "rfid_code":
                    dtoMap.put("rfid_code", product.getRfidCode()!= null ? product.getRfidCode() : "");
                    break;
                case "inventory_number":
                    dtoMap.put("inventory_number", product.getInventoryNumber()!= null ? product.getInventoryNumber() : "");
                    break;
                case "serial_number":
                    dtoMap.put("serial_number", product.getSerialNumber()!= null ? product.getSerialNumber() : "");
                    break;
                case "liable_id":
                    dtoMap.put("liable_id", product.getLiable() != null ? product.getLiable().getId() : "");
                    break;
                case "liable_name":
                    String liableName = (product.getLiable() != null && product.getLiable().getFirstname() != null && product.getLiable().getLastname() != null)
                            ? product.getLiable().getFirstname() + " " + product.getLiable().getLastname() : "";
                    dtoMap.put("liable_name", liableName);
                    break;


                case "receiver":
                    dtoMap.put("receiver", product.getReceiver()!= null ? product.getReceiver() : "");
                    break;
                case "kst_id":
                    dtoMap.put("kst_id", product.getKst() != null ? product.getKst().getId() : "");
                    break;
                case "asset_status_id":
                    dtoMap.put("asset_status_id", product.getAssetStatus() != null ? product.getAssetStatus().getId() : "");
                    break;
                case "unit_id":
                    dtoMap.put("unit_id", product.getUnit() != null ? product.getUnit().getId() : "");
                    break;
                case "branch_id":
                    dtoMap.put("branch_id", product.getBranch() != null ? product.getBranch().getId() : "");
                    break;
                case "location_id":
                    dtoMap.put("location_id", product.getLocation() != null ? product.getLocation().getId() : "");
                    break;
                case "mpk_id":
                    dtoMap.put("mpk_id", product.getMpk() != null ? product.getMpk().getId() : "");
                    break;
                case "type_id":
                    dtoMap.put("type_id", product.getType() != null ? product.getType().getId() : "");
                    break;
                case "subtype_id":
                    dtoMap.put("subtype_id", product.getSubtype() != null ? product.getSubtype().getId() : "");
                    break;

                case "kst":
                    dtoMap.put("kst", product.getKst() != null ? product.getKst().getKst() : "");
                    break;
                case "asset_status":
                    dtoMap.put("asset_status", product.getAssetStatus() != null ? product.getAssetStatus().getAssetStatus() : "");
                    break;
                case "unit":
                    dtoMap.put("unit", product.getUnit() != null ? product.getUnit().getUnit() : "");
                    break;
                case "branch":
                    dtoMap.put("branch", product.getBranch() != null ? product.getBranch().getBranch() : "");
                    break;
                case "location":
                    dtoMap.put("location", product.getLocation() != null ? product.getLocation().getLocation() : "");
                    break;
                case "mpk":
                    dtoMap.put("mpk", product.getMpk() != null ? product.getMpk().getMpk() : "");
                    break;
                case "type":
                    dtoMap.put("type", product.getType() != null ? product.getType().getType() : "");
                    break;
                case "subtype":
                    dtoMap.put("subtype", product.getSubtype() != null ? product.getSubtype().getSubtype() : "");
                    break;
                case "producer":
                    dtoMap.put("producer", product.getProducer()!= null ? product.getProducer() : "");
                    break;
                case "supplier":
                    dtoMap.put("supplier", product.getSupplier() != null ? product.getSupplier() : "");
                    break;
                case "scrapping":
                    dtoMap.put("scrapping", product.isScrapping());
                    break;
                case "scrapping_date":
                    dtoMap.put("scrapping_date", product.getScrappingDate()!= null ? product.getScrappingDate() : "");
                    break;
                case "scrapping_reason":
                    dtoMap.put("scrapping_reason", product.getScrappingReason()!= null ? product.getScrappingReason() : "");
                    break;
                case "document":
                    dtoMap.put("document", product.getDocument()!= null ? product.getDocument() : "");
                    break;
                case "document_date":
                    dtoMap.put("document_date", product.getDocumentDate()!= null ? product.getDocumentDate() : "");
                    break;
                case "warranty_period":
                    dtoMap.put("warranty_period", product.getWarrantyPeriod()!= null ? product.getWarrantyPeriod() : "");
                    break;
                case "inspection_date":
                    dtoMap.put("inspection_date", product.getInspectionDate()!= null ? product.getInspectionDate() : "");
                    break;
                case "longitude":
                    dtoMap.put("longitude", product.getLongitude()!= null ? product.getLongitude() : "");
                    break;
                case "latitude":
                    dtoMap.put("latitude", product.getLatitude()!= null ? product.getLatitude() : "");
                    break;
                case "comments":
                    dtoMap.put("comments", product.getComments() != null ? CommentMapper.toDTO(product.getComments()) : "");
                    break;
                default:
                    break;
            }
        }
        return dtoMap;
    }
}
