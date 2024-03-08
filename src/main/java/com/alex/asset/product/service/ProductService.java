package com.alex.asset.product.service;


import com.alex.asset.product.domain.Product;
import com.alex.asset.product.dto.ProductDto;
import com.alex.asset.product.dto.ScrapDto;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final String TAG = "PRODUCT_SERVICE - ";

    private final ProductMapper productMapper;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;


    public List<ProductDto> getAll() {
        log.info(TAG + "get all products");
        return productRepo.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<ProductDto> getActive() {
        log.info(TAG + "get all active products");
        return productRepo.getActive().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }


    public ProductDto create(ProductDto dto, Long id) {
        Product product = productMapper.toEntity(dto);
        product.setCreatedBy(userRepo.getUser(id));
        product.setLiable(userRepo.getUser(dto.getLiableId()));
        product.setBarCode(null);
        product.setRfidCode(null);
        return productMapper.toDto(productRepo.save(product));
    }

    public ProductDto getById(Long id) {
        return productMapper.toDto(productRepo.get(id));

    }


    public Map<Long, String> getByTitle(String title) {
        // it doesnt produce errors
        return productRepo.getByTitle(title)
                .stream()
                .collect(Collectors.toMap(Product::getId, Product::getTitle));

    }

    public boolean updateVisibility(DtoActive dto) {
        productRepo.updateVisibility(dto.isActive(), dto.getId());
        return true;
    }

    public boolean scraping(ScrapDto dto) {
        Product product = productRepo.get(dto.getId());
        product.setScrapping(dto.isScrap());
        product.setScrappingReason(dto.getScrappingReason());
        product.setScrappingDate(dto.getScrappingDate());
        product.setActive(!dto.isScrap());
        productRepo.save(product);
        return true;
    }

    public Boolean update(ProductDto dto) {
        Product product = updateProduct(productRepo.get(dto.getId()), dto);
        product.setActive(true);
        return true;
    }


    public Product updateProduct(Product existingProduct, ProductDto productDto) {
        BeanUtils.copyProperties(productDto, existingProduct, getNullPropertyNames(productDto));
        return existingProduct;
    }

    private String[] getNullPropertyNames(ProductDto source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


}
