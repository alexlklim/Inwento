package com.alex.asset.core.service;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.dto.ShortProduct;
import com.alex.asset.core.mappers.ProductMapper;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.core.repo.ProductRepo;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final String TAG = "PRODUCT_SERVICE - ";

    private final ProductRepo productRepo;
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final FieldService fieldService;
    private final TypeService typeService;


    public List<ProductDto> getAllByCompany(Long companyID) {
        log.info(TAG + "Try to get all Product and return ProductDto");
        List<ProductDto> productDTOs = new ArrayList<>();
        for (Product product : productRepo.findByActiveTrueAndCompany(companyRepo.getCompany(companyID))) {
            productDTOs.add(ProductMapper.toDto(product));
        }
        return productDTOs;
    }


    public Long addEmptyForCompany(Long companyId, Long userId) {
        log.info(TAG + "Create an empty new product for company");
        Company company = companyRepo.getCompany(companyId);
        Product product = new Product();
        product.setActive(false);
        product.setCreatedBy(userRepo.getUser(userId));
        product.setCompany(company);
        Product productFromDB = productRepo.save(product);
        return productFromDB.getId();
    }

    public ProductDto getProductByCompanyAndId(Long companyId, Long id) {
        log.info("Try to get  product with id: {}", id);
        Company company = companyRepo.getCompany(companyId);
        Product product = productRepo.findByActiveTrueAndIdAndCompany(id, company).orElse(null);
        if (product == null) {
            log.error(TAG + "Product is null with id: {}", id);
            return null;
        }
        return ProductMapper.toDto(product);
    }

    public boolean updateProductForCompanyById(
            ,
            Long id, ProductDto dto, CustomPrincipal principal) {
        log.info(TAG + "Try to update product");
        Company company = companyRepo.getCompany(principal.getCompanyId());

        Product productFromDb = productRepo.findByIdAndCompany(id, company).orElse(null);
        if (productFromDb == null) {
            log.info(TAG + "Product is null");
            return false;
        }

        Product product = productCreating(dto, company,userRepo.getUser(principal.getName()));
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
        log.info(TAG + "Try to make inactive product with id: {}", id);
        Company company = companyRepo.getCompany(principal.getCompanyId());
        if (company == null) return false;
        if (!productRepo.existsById(id)) return false;
        Product product = productRepo.findByActiveTrueAndIdAndCompany(id, company).orElse(null);
        if (product == null) return false;
        product.setActive(false);
        return true;
    }

    private Product productCreating(ProductDto dto, Company company, User user) {
        log.info(TAG + "Product creating process");
        Product product = ProductMapper.toEntity(dto);
        product.setCreatedBy(user);
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
        log.info(TAG + "Try to delete product with id: {}", id);
        Company company = companyRepo.getCompany(principal.getCompanyId());
        if (company == null) return false;
        if (!productRepo.existsById(id)) return false;
        Product product = productRepo.findByActiveFalseAndIdAndCompany(id, company).orElse(null);
        if (product == null) return false;
        productRepo.delete(product);
        return true;
    }


    public List<ShortProduct> getProductsByTitle(String title) {
        List<Product> products = productRepo.findByTitleContainingIgnoreCase(title);
        List<ShortProduct> shortProducts = new ArrayList<>();

        for (Product product : products) {
            System.out.println(product.getBarCode() + " " + product.getTitle());
            ;

            shortProducts.add(new ShortProduct(product.getBarCode(), product.getTitle()));
            System.out.println(product.getBarCode() + " " + product.getTitle());
            ;
        }


        return shortProducts;
    }

}
