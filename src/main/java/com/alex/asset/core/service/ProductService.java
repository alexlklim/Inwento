package com.alex.asset.core.service;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.mappers.ProductMapper;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.core.repo.ProductRepo;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final String TAG = "PRODUCT_SERVICE - ";

    private final ProductRepo productRepo;
    private final CompanyRepo companyRepo;
    private final  FieldService fieldService;
    private final  TypeService typeService;


    public List<ProductDto> getAll(UUID comapnyUUID) {
        log.info(TAG + "Try to get all Product and return ProductDto");
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        // if company doesn't exist return null
        if (company == null) {
            log.error(TAG + "Company is null");
            return null;
        }
        List<Product> products = productRepo.findByActiveTrueAndCompany(company);
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product: products){
            productDtos.add(ProductMapper.toDto(product));
        }
        return productDtos;
    }


    public Long add(CustomPrincipal principal) {
        log.info(TAG + "Create an empty new product for company {} by {}", principal.getComapnyUUID(), principal.getEmail());
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        // if company doesn't exist return null
        if (company == null) {
            log.error(TAG + "Company is null");
            return null;
        }
        Product product = new Product();
        product.setActive(false);
        product.setCreatedBy(principal.getUserUUID());
        product.setCompany(company);


        Product productFromDB = productRepo.save(product);
        log.info(TAG + "Product with id {} was added", productFromDB.getId());
        return productFromDB.getId();
    }
    public ProductDto getProductById(Long id, CustomPrincipal principal) {
        log.info("Try to get  product with id: {}", id);
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        // if company doesn't exist return null
        if (company == null) {
            log.error(TAG + "Company is null");
            return null;
        }
        Product product = productRepo.findByActiveTrueAndIdAndCompany(id, company).orElse(null);
        if (product == null) {
                log.error(TAG + "Product is null with id: {}", id);
            return null;
        }
        return ProductMapper.toDto(product);
    }

    public boolean update(Long id, ProductDto dto, CustomPrincipal principal) {
        log.info(TAG + "Try to add update product {} to company {} by {}", dto.getTitle(), principal.getComapnyUUID(), principal.getEmail());
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        // if company doesn't exist return null
        if (company == null) {

            return false;
        }

        Product productFromDb = productRepo.findByActiveTrueAndIdAndCompanyAndBarCode(id, company, dto.getBarCode()).orElse(null);
        if (productFromDb == null) return false;
        Product product = productCreating(dto, company, principal.getUserUUID());
        product.setId(id);
        product.setActive(true);
        product.setCreated(productFromDb.getCreated());
        product.setUpdated(product.getUpdated());
        product.setCreatedBy(productFromDb.getCreatedBy());
        product.setLastInventoryDate(productFromDb.getLastInventoryDate());
        product.setCompany(productFromDb.getCompany());
        product.setLastInventoryDate(productFromDb.getLastInventoryDate());
        productRepo.save(product);
        return true;
    }


    public boolean makeInactive(Long id, CustomPrincipal principal) {
        log.info(TAG + "Try to make inactive product with id: {}",  id);
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        if (company == null) return false;
        if (!productRepo.existsById(id)) return false;
        Product product = productRepo.findByActiveTrueAndIdAndCompany(id, company).orElse(null);
        if (product == null) return false;
        product.setActive(false);
        return true;
    }

    private Product productCreating(ProductDto dto, Company company, UUID userUUID){
        log.info(TAG + "Product creating process");
        Product product = ProductMapper.toEntity(dto);
        product.setCreatedBy(userUUID);
        product.setAssetStatus(fieldService.getAssetStatus(dto.getAssetStatus()));
        product.setUnit(fieldService.getUnit(dto.getUnit()));
        product.setKst(fieldService.getKST(dto.getKst()));

        product.setProducer(fieldService.getProducer(dto.getProducerName(), company));
        product.setSupplier(fieldService.getSupplier(dto.getSupplierName(), company));
        product.setBranch(fieldService.getBranch(dto.getBranchName(), company));
        product.setMpk(fieldService.getMPK(dto.getMpkName(), company));

        product.setType(typeService.getType(dto.getTypeName(), company));
        product.setSubtype(typeService.getSubtype(
                dto.getSubtypeName(),
                typeService.getType(dto.getTypeName(), company),
                company
        ));
        product.setCompany(company);
        return product;
    }


    public boolean delete(Long id, CustomPrincipal principal) {
        log.info(TAG + "Try to delete product with id: {}",  id);
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        if (company == null) return false;
        if (!productRepo.existsById(id)) return false;
        Product product = productRepo.findByActiveFalseAndIdAndCompany(id, company).orElse(null);
        if (product == null) return false;
        productRepo.delete(product);
        return true;
    }


}
