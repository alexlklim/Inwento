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

    private final ProductRepo productRepo;
    private final CompanyRepo companyRepo;
    private final  FieldService fieldService;
    private final  TypeService typeService;


    public List<ProductDto> getAll(UUID comapnyUUID) {
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        // if company doesn't exist return null
        if (company == null) return null;
        List<Product> products = productRepo.findByActiveTrueAndCompany(company);
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product: products){
            productDtos.add(ProductMapper.toDto(product));
        }
        return productDtos;
    }


//    public boolean add(ProductDto dto, CustomPrincipal principal) {
//        log.info("Try to add new product {} to company {} by {}", dto.getTitle(), principal.getComapnyUUID(), principal.getEmail());
//        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
//        // if company doesn't exist return null
//        if (company == null) return false;
//
//        if (productRepo.existsByInventoryNumberAndCompany(dto.getInventoryNumber(), company) ||
//        productRepo.existsByCodeAndCompany(dto.getCode(), company)){
//            log.error("Inventory number or code aren't unique");
//            return false;
//        }
//
//        productRepo.save(productCreating(dto, company, principal.getUserUUID()));
//
//        log.info("Product {} was added", dto.getTitle());
//        return true;
//    }


    public Long add(CustomPrincipal principal) {
        log.info("Create an empty new product for company {} by {}", principal.getComapnyUUID(), principal.getEmail());
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        // if company doesn't exist return null
        if (company == null) return null;

        Product product = new Product();
        product.setActive(true);
        product.setCreatedBy(principal.getUserUUID());
        product.setCompany(company);


        Product productFromDB = productRepo.save(product);
        log.info("Product with id {} was added", productFromDB.getId());
        return productFromDB.getId();
    }
    public ProductDto getProductById(Long id, CustomPrincipal principal) {
        log.info("Try to get  product with id: {}", id);
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        // if company doesn't exist return null
        if (company == null) return null;
        Product product = productRepo.findByActiveTrueAndIdAndCompany(id, company).orElse(null);
        if (product == null) return null;
        return ProductMapper.toDto(product);
    }

    public boolean update(Long id, ProductDto dto, CustomPrincipal principal) {
        log.info("Try to add update product {} to company {} by {}", dto.getTitle(), principal.getComapnyUUID(), principal.getEmail());
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        // if company doesn't exist return null
        if (company == null) return false;
        Product productFromDb = productRepo.findByActiveTrueAndIdAndCompany(id, company).orElse(null);
        if (productFromDb == null) return false;
        if (productRepo.existsByInventoryNumberAndCompany(dto.getInventoryNumber(), company) ||
                productRepo.existsByCodeAndCompany(dto.getCode(), company)){
            log.error("Inventory number or code aren't unique");
            return false;
        }
        Product product = productCreating(dto, company, principal.getUserUUID());
        product.setId(id);
        product.setCreated(productFromDb.getCreated());
        product.setUpdated(product.getUpdated());
        product.setCreatedBy(productFromDb.getCreatedBy());
        product.setLastInventoryDate(productFromDb.getLastInventoryDate());
        product.setCompany(productFromDb.getCompany());
        productRepo.save(product);
        return true;
    }


    public boolean delete(Long id, CustomPrincipal principal) {
        log.info("Try to delete product with id: {}",  id);
        Company company = companyRepo.findById(principal.getComapnyUUID()).orElse(null);
        if (company == null) return false;
        if (!productRepo.existsById(id)) return false;
        Product product = productRepo.findByActiveTrueAndIdAndCompany(id, company).orElse(null);
        if (product == null) return false;
        product.setActive(false);
        return true;
    }

    private Product productCreating(ProductDto dto, Company company, UUID userUUID){
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

}
