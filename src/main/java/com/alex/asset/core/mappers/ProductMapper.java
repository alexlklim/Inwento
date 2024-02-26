package com.alex.asset.core.mappers;

import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import com.alex.asset.core.domain.fields.*;
import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.service.FieldService;
import com.alex.asset.core.service.TypeService;
import com.alex.asset.security.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;



@RequiredArgsConstructor
@Service
public class ProductMapper {
    private final TypeService typeService;
    private final FieldService fieldService;


    public Product toEntity(Company company, Product product, ProductDto dto) {
        product.setActive(true);
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        product.setBarCode(dto.getBarCode());
        product.setRfidCode(dto.getRfidCode());

        product.setReceiver(dto.getReceiver());


        product.setDocument(dto.getDocument());
        product.setDocumentDate(dto.getDocumentDate());
        product.setWarrantyPeriod(dto.getWarrantyPeriod());
        product.setInspectionDate(dto.getInspectionDate());
        product.setLastInventoryDate(dto.getLastInventoryDate());

        product.setLongitude(dto.getLocLongitude());
        product.setLatitude(dto.getLocLatitude());
        product.setAssetStatus(fieldService.getAssetStatus(dto.getAssetStatus()));
        product.setUnit(fieldService.getUnit(dto.getUnit()));
        product.setKst(fieldService.getKST(dto.getKst()));


        product.setProducer(dto.getProducer());
        product.setSupplier(dto.getSupplier());
        product.setBranch(fieldService.getBranch(dto.getBranchName(), company));
        product.setMpk(fieldService.getMPK(dto.getMpkName(), company));

        product.setType(typeService.getType(dto.getTypeName(), company));
        product.setSubtype(typeService.getSubtype(
                dto.getSubtypeName(),
                typeService.getType(dto.getTypeName(), company),
                company
        ));

        return product;

    }





    public ProductDto toDto(Product entity){
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());


        dto.setBarCode(entity.getBarCode());
        dto.setRfidCode(entity.getRfidCode());

        dto.setCreated(entity.getCreated().toLocalDate());
        dto.setUpdated(entity.getUpdated().toLocalDate());

        dto.setProducer(entity.getProducer());
        dto.setSupplier(entity.getSupplier());

        Fio createdBy = new Fio();
        setField(entity.getCreatedBy(), createdBy::setName, User::getFirstname);
        setField(entity.getCreatedBy(), createdBy::setSurname, User::getLastname);
        dto.setCreatedByName(createdBy.getName() + " " + createdBy.getSurname());

        dto.setReceiver(entity.getReceiver());


        Fio nameLiable = new Fio();
        setField(entity.getLiable(), nameLiable::setName, User::getFirstname);
        setField(entity.getLiable(), nameLiable::setSurname, User::getLastname);
        dto.setLiableName(nameLiable.getName() + " " + nameLiable.getSurname());

        setField(entity.getUnit(), dto::setUnit, Unit::getUnit);
        setField(entity.getAssetStatus(), dto::setAssetStatus, AssetStatus::getAssetStatus);
        setField(entity.getKst(), dto::setKst, KST::getKst);

        setField(entity.getType(), dto::setTypeName, Type::getType);
        setField(entity.getSubtype(), dto::setSubtypeName, Subtype::getSubtype);
        setField(entity.getBranch(), dto::setBranchName, Branch::getBranch);

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

    @NoArgsConstructor
    @Data
    private static class Fio{
        String name;
        String surname;
    }




    private <T, R> void setField(T object, Consumer<R> setter, Function<T, R> getter) {
        Optional.ofNullable(object).map(getter).ifPresent(setter);
    }





}
