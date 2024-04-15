package com.alex.asset.product.mappers;

import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.inventory.domain.event.InventoryStatus;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.dto.ProductV1Dto;
import com.alex.asset.product.dto.ProductV2Dto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ProductMapper {
    private final ConfigureService service;
    private final TypeService typeService;
    private final LocationService locationService;


    @SneakyThrows
    public ProductV1Dto toDto(Product product) {
        return ProductV1Dto.builder()
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


    public Product toEntity(ProductV1Dto dto) {
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


    public ProductV2Dto toProductV2Dto(Product product, InventoryStatus inventoryStatus) {
        return new ProductV2Dto().toBuilder()
                .id(product.getId())
                .title(product.getTitle())
                .barCode(product.getBarCode())
                .inventoryStatus(inventoryStatus)
                .build();
    }


    public ProductHistoryDto toProductHistoryDto(ProductHistory productHistory) {
        return new ProductHistoryDto().toBuilder()
                .created(productHistory.getCreated())
                .username(productHistory.getUser().getFirstname() + productHistory.getUser().getLastname())
                .email(productHistory.getUser().getEmail())
                .activity(productHistory.getActivity())
                .build();
    }


}
