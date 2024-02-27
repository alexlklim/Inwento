package com.alex.asset.core.service;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.Product;
import com.alex.asset.core.dto.ProductDto;
import com.alex.asset.core.mappers.ProductMapper;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.core.repo.ProductRepo;
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

    private final ProductMapper productMapper;
    private final ProductRepo productRepo;
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;



    @Transactional
    public List<ProductDto> getAllProductsForCompany(Long companyID) {
        log.info(TAG + "Try to get all Product and return ProductDto");
        List<ProductDto> productDTOs = new ArrayList<>();
        for (Product product : productRepo.findByActiveTrueAndCompany(companyRepo.getCompany(companyID))) {
            productDTOs.add(productMapper.toDto(product));
        }
        return productDTOs;
    }


    public Long addEmptyProductForCompany(Long companyId, Long userId) {
        log.info(TAG + "Create an empty new product for company");
        Product product = new Product();
        product.setActive(false);
        product.setCreatedBy(userRepo.getUser(userId));
        Product productFromDB = productRepo.save(product);
        return productFromDB.getId();
    }

    public ProductDto getProductByIdForCompany(Long companyId, Long id) {
        log.info("Try to get  product with id: {}", id);
        Product product = productRepo
                .findByActiveTrueAndIdAndCompany(
                        id, companyRepo.getCompany(companyId)).orElse(null);
        if (product == null) {
            log.error(TAG + "Product is null with id: {}", id);
            return null;
        }
        return productMapper.toDto(product);
    }

    public boolean updateProductByIdForCompany(Long companyId, Long productId, ProductDto dto){
        log.info(TAG + "Try to update product");
        Company company = companyRepo.getCompany(companyId);
        Product productFromDb = productRepo.findByCompanyAndId(company, productId).orElse(null);
        if (productFromDb == null) {
            log.info(TAG + "Product is null");
            return false;
        }

        Product product = productMapper.toEntity(company, productFromDb, dto);
        product.setActive(true);;

        productRepo.save(product);
        return true;
    }



    public boolean makeProductInvisibleByIdForCompany(Long companyId, Long productId) {
        log.info(TAG + "Try to make inactive product with id: {}", productId);
        if (!productRepo.existsById(productId)) return false;
        Product product = productRepo
                .findByCompanyAndIdAndActiveTrue(
                        companyRepo.getCompany(companyId), productId).orElse(null);
        if (product == null) return false;
        product.setActive(false);
        return true;
    }


    public boolean deleteProductByIdForCompany(Long companyId, Long productId) {
        Product product = productRepo
                .findByCompanyAndIdAndActiveFalse(
                        companyRepo.getCompany(companyId), productId).orElse(null);
        if (product == null) return false;
        productRepo.delete(product);
        return true;
    }


    public List<ShortProduct> getProductsByTitleForCompany(Long companyId, String title) {
        List<Product> products = productRepo
                .findByCompanyAndTitleContainingIgnoreCase(companyRepo.getCompany(companyId), title);
        List<ShortProduct> shortProducts = new ArrayList<>();

        for (Product product : products) {
            shortProducts.add(new ShortProduct(product.getBarCode(), product.getTitle()));
        }
        return shortProducts;
    }

}
