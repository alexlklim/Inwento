package com.alex.asset.product.service;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Activity;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.product.dto.ProductDto;
import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.dto.ScrapDto;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductHistoryRepo;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final ProductHistoryRepo productHistoryRepo;
    private final UserRepo userRepo;


    public List<ProductDto> getAll() {
        log.info(TAG + "get all products");
        return productRepo.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<ProductDto> getActive() {
        log.info(TAG + "get all active products");
        return getActiveProducts()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<Product> getActiveProducts() {
        return productRepo.getActive();
    }


    public List<Product> getActiveProductsByBranch(Branch branch) {
        return productRepo.findAllByBranch(branch);
    }




    public ProductDto create(ProductDto dto, Long userId) {
        log.info(TAG + "Create product by user with id {}", userId);
        Product product = productMapper.toEntity(dto);
        product.setCreatedBy(userRepo.getUser(userId));
        product.setLiable(userRepo.getUser(dto.getLiableId()));
        product.setBarCode(null);
        product.setRfidCode(null);
        Product productFromDB = productRepo.save(product);
        logService.addLog(userId, Action.CREATE, Section.PRODUCT, dto.toString());
        addHistoryToProduct(userId, productFromDB.getId(), Activity.PRODUCT_WAS_CREATED);
        return productMapper.toDto(productFromDB);
    }

    @SneakyThrows
    public ProductDto getById(Long productId) {
        log.info(TAG + "get product by id");
        return productMapper.toDto(productRepo.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + productId + " not found"))
        );

    }


    public Map<Long, String> getByTitle(String title) {
        log.info(TAG + "get product by title");
        return productRepo.getByTitle(title)
                .stream()
                .collect(Collectors.toMap(Product::getId, Product::getTitle));

    }

    @SneakyThrows
    public void updateVisibility(DtoActive dto, Long userId) {
        log.info(TAG + "Update product visibility by user with id {}", userId);
        Product product = productRepo.findById(dto.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + dto.getId() + " not found")
        );
        product.setActive(dto.isActive());
        productRepo.save(product);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, dto.toString());
        addHistoryToProduct(userId, product.getId(), Activity.VISIBILITY);

    }

    @SneakyThrows
    public void scraping(ScrapDto dto, Long userId) {
        log.info(TAG + "Scrap product with id {} by user with id {}", dto.getId(), userId);
        Product product = productRepo.findById(dto.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + dto.getId() + " not found")
        );
        product.setScrapping(dto.isScrap());
        product.setScrappingReason(dto.getScrappingReason());
        product.setScrappingDate(dto.getScrappingDate());
        product.setActive(!dto.isScrap());
        productRepo.save(product);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, dto.toString());
        addHistoryToProduct(userId, product.getId(), Activity.SCRAPPING);
    }

    public void update(ProductDto dto, Long userId) {
        log.info(TAG + "Update product with id {} by user with id {}", dto.getId(), userId);
        Product oldProduct = productRepo.findById(dto.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + dto.getId() + " not found")
                );

        Product updatedProduct = new Product();
        BeanUtils.copyProperties(oldProduct, updatedProduct);
        BeanUtils.copyProperties(dto, updatedProduct, getNullPropertyNames(dto));

        updatedProduct.setActive(true);
        addHistoryAboutChangedData(oldProduct, updatedProduct, userId);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, "Product was changed");
        productRepo.save(updatedProduct);
    }


    @SneakyThrows
    public List<ProductHistoryDto> getHistoryOfProductById(Long productId) {
        log.info(TAG + "Get history of product with id {}", productId);
        Product product = productRepo.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + productId + " was not found")
        );
        return productHistoryRepo.findAllByProductOrderByCreatedDesc(product)
                .stream()
                .map(productMapper::toProductHistoryDto)
                .collect(Collectors.toList());
    }





    public void productMovedToAnotherBranch(Product product, Branch branch) {
        log.info(TAG + "product with id {} moved to another branch", product.getId());
        product.setBranch(branch);
        productRepo.save(product);
    }

    public Optional<Product> getByBarCode(String barCode) {
        log.info(TAG + "Get product by bar code {}", barCode);
        return productRepo.getByBarCode(barCode);
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


    private void addHistoryAboutChangedData(Product oldProduct, Product updatedProduct, Long userId) {
        log.info(TAG + "Add history to product with id {}", oldProduct.getId());

        if (!Objects.equals(oldProduct.getTitle(), updatedProduct.getTitle()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.TITLE);

        if (!Objects.equals(oldProduct.getPrice(), updatedProduct.getPrice()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.PRICE);

        if (!Objects.equals(oldProduct.getBarCode(), updatedProduct.getBarCode()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.BAR_CODE);

        if (!Objects.equals(oldProduct.getRfidCode(), updatedProduct.getRfidCode()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.RFID_CODE);

        if (!Objects.equals(oldProduct.getInventoryNumber(), updatedProduct.getInventoryNumber()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.INVENTORY_NUMBER);

        if (!Objects.equals(oldProduct.getSerialNumber(), updatedProduct.getSerialNumber()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.SERIAL_NUMBER);

        if (!Objects.equals(oldProduct.getLiable(), updatedProduct.getLiable()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.LIABLE);

        if (!Objects.equals(oldProduct.getReceiver(), updatedProduct.getReceiver()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.RECEIVER);

        if (!Objects.equals(oldProduct.getKst(), updatedProduct.getKst()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.KST);

        if (!Objects.equals(oldProduct.getAssetStatus(), updatedProduct.getAssetStatus()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.ASSET_STATUS);

        if (!Objects.equals(oldProduct.getUnit(), updatedProduct.getUnit()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.UNIT);

        if (!Objects.equals(oldProduct.getBranch(), updatedProduct.getBranch()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.BRANCH);

        if (!Objects.equals(oldProduct.getMpk(), updatedProduct.getMpk()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.MPK);

        if (!Objects.equals(oldProduct.getType(), updatedProduct.getType()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.TYPE);

        if (!Objects.equals(oldProduct.getSubtype(), updatedProduct.getSubtype()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.SUBTYPE);

        if (!Objects.equals(oldProduct.getProducer(), updatedProduct.getProducer()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.PRODUCER);

        if (!Objects.equals(oldProduct.getSupplier(), updatedProduct.getSupplier()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.SUPPLIER);

        if (oldProduct.isScrapping() != updatedProduct.isScrapping())
            addHistoryToProduct(userId, oldProduct.getId(), Activity.SCRAPPING);

        if (!Objects.equals(oldProduct.getDocument(), updatedProduct.getDocument()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.DOCUMENT);

        if (!Objects.equals(oldProduct.getDocumentDate(), updatedProduct.getDocumentDate()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.DOCUMENT_DATE);

        if (!Objects.equals(oldProduct.getWarrantyPeriod(), updatedProduct.getWarrantyPeriod()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.WARRANTY_PERIOD);

        if (!Objects.equals(oldProduct.getInspectionDate(), updatedProduct.getInspectionDate()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.INSPECTION_DATE);

        if (!Objects.equals(oldProduct.getLongitude(), updatedProduct.getLongitude()) ||
                !Objects.equals(oldProduct.getLatitude(), updatedProduct.getLatitude()))
            addHistoryToProduct(userId, oldProduct.getId(), Activity.GPS);
    }

    private void addHistoryToProduct(Long userId, Long productId, Activity activity) {
        ProductHistory productHistory = new ProductHistory();
        productHistory.setActivity(activity);
        productHistory.setCreated(LocalDateTime.now());
        productHistory.setProduct(productRepo.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + productId + " not found"))
        );
        productHistory.setUser(userRepo.findById(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + productId + " not found"))
        );

        productHistoryRepo.save(productHistory);

    }


}
