package com.alex.asset.product.mappers;

import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.inventory.domain.event.InventoryStatus;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.product.dto.ProductDTO;
import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.dto.ProductV2Dto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class ProductMapper {
    private final ConfigureService service;
    private final TypeService typeService;
    private final LocationService locationService;


    @SneakyThrows
    public ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .active(product.isActive())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .barCode(product.getBarCode())
                .rfidCode(product.getRfidCode())
                .inventoryNumber(product.getInventoryNumber())
                .serialNumber(product.getSerialNumber())
                .liableId(product.getLiable() != null ? product.getLiable().getId() : null)
                .receiver(product.getReceiver())
                .kstId(product.getKst() != null ? product.getKst().getId() : null)
                .assetStatusId(product.getAssetStatus() != null ? product.getAssetStatus().getId() : null)
                .unitId(product.getUnit() != null ? product.getUnit().getId() : null)
                .branchId(product.getBranch() != null ? product.getBranch().getId() : null)
                .mpkId(product.getMpk() != null ? product.getMpk().getId() : null)
                .typeId(product.getType() != null ? product.getType().getId() : null)
                .subtypeId(product.getSubtype() != null ? product.getSubtype().getId() : null)
                .producer(product.getProducer())
                .supplier(product.getSupplier())
                .scrapping(product.isScrapping())
                .scrappingDate(product.getScrappingDate())
                .scrappingReason(product.getScrappingReason())
                .document(product.getDocument())
                .documentDate(product.getDocumentDate())
                .warrantyPeriod(product.getWarrantyPeriod())
                .inspectionDate(product.getInspectionDate())
                .longitude(product.getLongitude())
                .latitude(product.getLatitude())

                .liableName(product.getLiable() != null ?
                        product.getLiable().getFirstname() + " " + product.getLiable().getLastname() : null)


                .kst(product.getKst() != null ? product.getKst().getNum() : null)
                .assetStatus(product.getAssetStatus() != null ? product.getAssetStatus().getAssetStatus() : null)
                .unit(product.getUnit() != null ? product.getUnit().getUnit() : null)
                .branch(product.getBranch() != null ? product.getBranch().getBranch() : null)
                .location(product.getLocation() != null ? product.getLocation().getLocation() : null)
                .mpk(product.getMpk() != null ? product.getMpk().getMpk() : null)
                .type(product.getType() != null ? product.getType().getType() : null)
                .subtype(product.getSubtype() != null ? product.getSubtype().getSubtype() : null)

                .build();
    }


    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setBarCode(dto.getBarCode());
        product.setRfidCode(dto.getRfidCode());
        product.setReceiver(dto.getReceiver());
        product.setProducer(dto.getProducer());
        product.setSupplier(dto.getSupplier());
        product.setScrapping(false);
        product.setKst(service.getKSTById(dto.getKstId()));
        product.setAssetStatus(service.getAssetStatusById(dto.getAssetStatusId()));
        product.setUnit(service.getUnitById(dto.getUnitId()));
        product.setBranch(locationService.getBranchById(dto.getBranchId()));
        product.setLocation(locationService.getLocationById(dto.getLocationId()));
        product.setMpk(service.getMPKById(dto.getMpkId()));
        product.setType(typeService.getTypeById(dto.getTypeId()));
        product.setSubtype(typeService.getSubtypeById(dto.getSubtypeId()));
        product.setDocument(dto.getDocument());
        product.setDocumentDate(dto.getDocumentDate());
        product.setWarrantyPeriod(dto.getWarrantyPeriod());
        product.setInspectionDate(dto.getInspectionDate());
        product.setLongitude(dto.getLongitude());
        product.setLatitude(dto.getLatitude());
        return product;
    }


    public ProductHistoryDto toProductHistoryDto(ProductHistory productHistory) {
        return new ProductHistoryDto().toBuilder()
                .created(productHistory.getCreated())
                .username(productHistory.getUser().getFirstname() + productHistory.getUser().getLastname())
                .email(productHistory.getUser().getEmail())
                .activity(productHistory.getActivity())
                .build();
    }


    public Map<String, Object> toDTOWithCustomFields(Product product, List<String> fields) {
        Map<String, Object> dtoMap = new HashMap<>();
        dtoMap.put("id", product.getId());
        dtoMap.put("active", product.isActive());
        dtoMap.put("title", product.getTitle());
        for (String field : fields) {
            switch (field) {
                case "description":
                    dtoMap.put("description", product.getDescription());
                    break;
                case "price":
                    dtoMap.put("price", product.getPrice());
                    break;
                case "bar_code":
                    dtoMap.put("bar_code", product.getBarCode());
                    break;
                case "rfid_code":
                    dtoMap.put("rfid_code", product.getRfidCode());
                    break;
                case "inventory_number":
                    dtoMap.put("inventory_number", product.getInventoryNumber());
                    break;
                case "serial_number":
                    dtoMap.put("serial_number", product.getSerialNumber());
                    break;
                case "liable_id":
                    dtoMap.put("liable_id", product.getLiable() != null ? product.getLiable().getId() : null);
                    break;
                case "liable_name":
                    String liableName = (product.getLiable() != null && product.getLiable().getFirstname() != null && product.getLiable().getLastname() != null)
                            ? product.getLiable().getFirstname() + " " + product.getLiable().getLastname() : null;
                    dtoMap.put("liable_name", liableName);
                    break;


                case "receiver":
                    dtoMap.put("receiver", product.getReceiver());
                    break;
                case "kst_id":
                    dtoMap.put("kst_id", product.getKst() != null ? product.getKst().getId() : null);
                    break;
                case "asset_status_id":
                    dtoMap.put("asset_status_id", product.getAssetStatus() != null ? product.getAssetStatus().getId() : null);
                    break;
                case "unit_id":
                    dtoMap.put("unit_id", product.getUnit() != null ? product.getUnit().getId() : null);
                    break;
                case "branch_id":
                    dtoMap.put("branch_id", product.getBranch() != null ? product.getBranch().getId() : null);
                    break;
                case "location_id":
                    dtoMap.put("location_id", product.getLocation() != null ? product.getLocation().getId() : null);
                    break;
                case "mpk_id":
                    dtoMap.put("mpk_id", product.getMpk() != null ? product.getMpk().getId() : null);
                    break;
                case "type_id":
                    dtoMap.put("type_id", product.getType() != null ? product.getType().getId() : null);
                    break;
                case "subtype_id":
                    dtoMap.put("subtype_id", product.getSubtype() != null ? product.getSubtype().getId() : null);
                    break;

                case "kst":
                    dtoMap.put("kst", product.getKst() != null ? product.getKst().getKst() : null);
                    break;
                case "asset_status":
                    dtoMap.put("asset_status", product.getAssetStatus() != null ? product.getAssetStatus().getAssetStatus() : null);
                    break;
                case "unit":
                    dtoMap.put("unit", product.getUnit() != null ? product.getUnit().getUnit() : null);
                    break;
                case "branch":
                    dtoMap.put("branch", product.getBranch() != null ? product.getBranch().getBranch() : null);
                    break;
                case "location":
                    dtoMap.put("location", product.getLocation() != null ? product.getLocation().getLocation() : null);
                    break;
                case "mpk":
                    dtoMap.put("mpk", product.getMpk() != null ? product.getMpk().getMpk() : null);
                    break;
                case "type":
                    dtoMap.put("type", product.getType() != null ? product.getType().getType() : null);
                    break;
                case "subtype":
                    dtoMap.put("subtype", product.getSubtype() != null ? product.getSubtype().getSubtype() : null);
                    break;
                case "producer":
                    dtoMap.put("producer", product.getProducer());
                    break;
                case "supplier":
                    dtoMap.put("supplier", product.getSupplier());
                    break;
                case "scrapping":
                    dtoMap.put("scrapping", product.isScrapping());
                    break;
                case "scrapping_date":
                    dtoMap.put("scrapping_date", product.getScrappingDate());
                    break;
                case "scrapping_reason":
                    dtoMap.put("scrapping_reason", product.getScrappingReason());
                    break;
                case "document":
                    dtoMap.put("document", product.getDocument());
                    break;
                case "document_date":
                    dtoMap.put("document_date", product.getDocumentDate());
                    break;
                case "warranty_period":
                    dtoMap.put("warranty_period", product.getWarrantyPeriod());
                    break;
                case "inspection_date":
                    dtoMap.put("inspection_date", product.getInspectionDate());
                    break;
                case "longitude":
                    dtoMap.put("longitude", product.getLongitude());
                    break;
                case "latitude":
                    dtoMap.put("latitude", product.getLatitude());
                    break;
                default:
                    break;
            }
        }
        return dtoMap;
    }
    public ProductV2Dto toProductV2Dto(Product product, InventoryStatus inventoryStatus) {
        return new ProductV2Dto().toBuilder()
                .id(product.getId())
                .title(product.getTitle())
                .barCode(product.getBarCode())
                .inventoryStatus(inventoryStatus)
                .build();
    }

}
