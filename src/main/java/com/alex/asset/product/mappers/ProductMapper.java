package com.alex.asset.product.mappers;

import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.inventory.domain.event.InventoryStatus;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.dto.ProductDto;
import com.alex.asset.product.dto.ProductV2Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ProductMapper {


    private final ConfigureService service;
    private final TypeService typeService;


    public ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setActive(product.isActive());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setBarCode(product.getBarCode());
        dto.setRfidCode(product.getRfidCode());
        dto.setLiableId(product.getLiable() != null ? product.getLiable().getId() : null);
        dto.setReceiver(product.getReceiver());
        dto.setKstId(product.getKst() != null ? product.getKst().getId() : null);
        dto.setAssetStatusId(product.getAssetStatus() != null ? product.getAssetStatus().getId() : null);
        dto.setUnitId(product.getUnit() != null ? product.getUnit().getId() : null);
        dto.setBranchId(product.getBranch() != null ? product.getBranch().getId() : null);
        dto.setMpkId(product.getMpk() != null ? product.getMpk().getId() : null);
        dto.setTypeId(product.getType() != null ? product.getType().getId() : null);
        dto.setSubtypeId(product.getSubtype() != null ? product.getSubtype().getId() : null);
        dto.setProducer(product.getProducer());
        dto.setSupplier(product.getSupplier());
        dto.setScrapping(product.isScrapping());
        dto.setScrappingDate(product.getScrappingDate());
        dto.setScrappingReason(product.getScrappingReason());
        dto.setDocument(product.getDocument());
        dto.setDocumentDate(product.getDocumentDate());
        dto.setWarrantyPeriod(product.getWarrantyPeriod());
        dto.setInspectionDate(product.getInspectionDate());
        dto.setLongitude(product.getLongitude());
        dto.setLatitude(product.getLatitude());

        return dto;
    }


    public Product toEntity(ProductDto dto) {
        Product product = new Product();

        product.setActive(true);
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
        product.setBranch(service.getBranchById(dto.getBranchId()));
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
        ProductV2Dto dto = new ProductV2Dto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setBarCode(product.getBarCode());
        dto.setInventoryStatus(inventoryStatus);
        return dto;
    }


}
