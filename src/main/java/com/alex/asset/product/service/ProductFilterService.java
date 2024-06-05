package com.alex.asset.product.service;

import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.product.repo.ServicedAssetRepo;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dictionaries.UtilProduct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductFilterService {

    private final String TAG = "PRODUCT_SERVICE - ";

    private final ServicedAssetRepo servicedAssetRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;


    @SneakyThrows
    public Map<String, Object> getById(List<String> productFields, Long productId, Long userId) {
        log.info(TAG + "get product by id");
        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id " + userId));
        if (!product.isActive() && (userRepo.getUser(userId).getRoles() != Role.ADMIN)) {
            throw new ResourceNotFoundException("Product was deleted with id " + userId);
        }
        if (productFields.contains(UtilProduct.SERVICED_HISTORY)){
            return ProductMapper.toDTOWithCustomFields(product, productFields, servicedAssetRepo.findAllByProduct(product));
        }
        return ProductMapper.toDTOWithCustomFields(product, productFields);
    }

    @SneakyThrows
    public List<Map<String, Object>> getAllProducts(
            String mode, Boolean isScrap, List<String> productFields, Long userId) {
        List<Product> products = null;
        if (Role.ADMIN == Role.fromString(mode)) {
            if (Role.ADMIN == userRepo.getUser(userId).getRoles()) {
                products = productRepo.getProductsListByAdmin(true, isScrap);
            }
        } else products = productRepo.getProductsListByEmp(isScrap);

        if (productFields == null || productFields.isEmpty()) productFields = UtilProduct.getAll();
        if (products == null) return Collections.emptyList();
        return ProductMapper.toDTOsWithCustomFields(products, productFields);
    }



    @SneakyThrows
    public Map<String, Object> getByUniqueValues(
            String barCode, String rfidCode, String inventoryNumber, String serialNumber,
            List<String> productFields, Long userId) {
        barCode = "null".equals(barCode) ? null : barCode;
        rfidCode = "null".equals(rfidCode) ? null : rfidCode;
        inventoryNumber = "null".equals(inventoryNumber) ? null : inventoryNumber;
        serialNumber = "null".equals(serialNumber) ? null : serialNumber;
        Product product = productRepo.findByUniqueValues(barCode, rfidCode, inventoryNumber, serialNumber).orElse(null);
        if (product != null) {
            if (!product.isActive() && (userRepo.getUser(userId).getRoles() != Role.ADMIN)) {
                throw new ResourceNotFoundException("Product was deleted with id " + userId);
            }
            if (productFields == null || productFields.isEmpty()) productFields = UtilProduct.getAll();
            return ProductMapper.toDTOWithCustomFields(product, productFields);
        } else throw new ResourceNotFoundException("Product not found with this code ");
    }


    @SneakyThrows
    public List<Map<String, Object>> getByValue(String keyWord, Boolean isScrapped, List<String> productFields) {
        log.info(TAG + "get product by title");
        if (productFields == null || productFields.isEmpty()) productFields = UtilProduct.getAll();
        return ProductMapper.toDTOsWithCustomFields(productRepo.getByKeyWord(keyWord, isScrapped), productFields);
    }

    public List<Map<String, Object>> getByWarrantyPeriod(String startDate, String endDate) {
        return ProductMapper.toDTOsWithCustomFields(
                productRepo.getProductsByWarrantyPeriod(
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate)
                ),
                UtilProduct.getAll());

    }


    public List<Map<String, Object>> getByInspectionPeriod(String startDate, String endDate) {
        return ProductMapper.toDTOsWithCustomFields(
                productRepo.getProductsByInspectionPeriod(
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate)
                ),
                UtilProduct.getAll());

    }


}
