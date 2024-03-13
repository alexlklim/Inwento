package com.alex.asset.product.service;


import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.dto.ProductDto;
import com.alex.asset.product.dto.ScrapDto;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.expceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final String TAG = "PRODUCT_SERVICE - ";
    private final LogService logService;

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


    public ProductDto create(ProductDto dto, Long userId) {
        log.info(TAG + "Create product by user with id {}", userId);
        Product product = productMapper.toEntity(dto);
        product.setCreatedBy(userRepo.getUser(userId));
        product.setLiable(userRepo.getUser(dto.getLiableId()));
        product.setBarCode(null);
        product.setRfidCode(null);
        logService.addLog(userId, Action.CREATE, Section.PRODUCT, dto.toString());
        return productMapper.toDto(productRepo.save(product));
    }

    @SneakyThrows
    public ProductDto getById(Long productId) {
        log.info(TAG + "get product by id");
        return productMapper.toDto(productRepo.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + productId + " not found")
        ));

    }


    public Map<Long, String> getByTitle(String title) {
        log.info(TAG + "get product by title");
        return productRepo.getByTitle(title)
                .stream()
                .collect(Collectors.toMap(Product::getId, Product::getTitle));

    }

    @SneakyThrows
    public boolean updateVisibility(DtoActive dto, Long userId) {
        log.info(TAG + "Update product visibility by user with id {}", userId);
        Product product = productRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + dto.getId() + " not found"));
        product.setActive(dto.isActive());
        productRepo.save(product);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, dto.toString());
        return true;
    }

    @SneakyThrows
    public boolean scraping(ScrapDto dto, Long userId) {
        log.info(TAG + "Scrap product with id {} by user with id {}",dto.getId(), userId);
        Product product = productRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + dto.getId() + " not found"));
        product.setScrapping(dto.isScrap());
        product.setScrappingReason(dto.getScrappingReason());
        product.setScrappingDate(dto.getScrappingDate());
        product.setActive(!dto.isScrap());
        productRepo.save(product);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, dto.toString());
        return true;
    }

    @SneakyThrows
    public void update(ProductDto dto, Long userId) {
        log.info(TAG + "Update product with id {} by user with id {}",dto.getId(), userId);
        Product product = updateProduct(productRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + dto.getId() + " not found")
        ), dto);
        product.setActive(true);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, dto.toString());
    }


    public Optional<Product> getByBarCode(String barCode) {
        return productRepo.getByBarCode(barCode);
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
