package com.alex.asset.product.service;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Activity;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductHistoryRepo;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.Utils;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import com.alex.asset.utils.exceptions.errors.ValueIsNotUnique;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final String TAG = "PRODUCT_SERVICE - ";
    private final LogService logService;
    private final ProductRepo productRepo;
    private final ProductHistoryRepo productHistoryRepo;
    private final UserRepo userRepo;
    private final ConfigureService configureService;
    private final TypeService typeService;
    private final LocationService locationService;

    @Override
    @SneakyThrows
    public Map<String, Object> getById(List<String> productFields, Long productId, Long userId) {
        log.info(TAG + "get product by id");
        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id " + userId));
        if (!product.isActive() && (userRepo.getUser(userId).getRoles() != Role.ADMIN)) {
            throw new ResourceNotFoundException("Product was deleted with id " + userId);
        }
        if (productFields == null || productFields.isEmpty()) productFields = Utils.PRODUCT_FIELDS;
        return ProductMapper.toDTOWithCustomFields(product, productFields);
    }

    @Override
    @SneakyThrows
    public List<Map<String, Object>> getAllProducts(
            String mode, Boolean isScrap, List<String> productFields, Long userId) {
        List<Product> products = null;
        if (Role.ADMIN == Role.fromString(mode)) {
            if (Role.ADMIN == userRepo.getUser(userId).getRoles()) products = productRepo.getAllProducts();
        } else products = isScrap ? productRepo.getActive() : productRepo.getActiveWithoutScrapped();
        if (productFields == null || productFields.isEmpty()) productFields = Utils.PRODUCT_FIELDS;
        if (products == null) return Collections.emptyList();
        return getProductDTOs(products, productFields);
    }

    private List<Map<String, Object>> getProductDTOs(List<Product> products, List<String> productFields) {
        return products.stream().map(product -> ProductMapper.toDTOWithCustomFields(product, productFields)).toList();
    }

    @Override
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
            if (productFields == null || productFields.isEmpty()) productFields = Utils.PRODUCT_FIELDS;
            return ProductMapper.toDTOWithCustomFields(product, productFields);
        } else return Collections.emptyMap();
    }


    @Override
    @SneakyThrows
    public List<Map<String, Object>> getByValue(String keyWord, List<String> productFields) {
        log.info(TAG + "get product by title");
        if (productFields == null || productFields.isEmpty()) productFields = Utils.PRODUCT_FIELDS;
        return getProductDTOs(productRepo.getByKeyWord(keyWord), productFields);
    }

    @Override
    @Modifying
    @SneakyThrows
    public void update(Map<String, Object> updates, Long userId) {
        log.info(TAG + "Update or create product");
        if (updates.containsKey("id")) {
            Long productId = ((Number) updates.get("id")).longValue();
            Product product = productRepo.findById(productId).orElseThrow(
                    () -> new ResourceNotFoundException("Product not found"));
            updates.remove("id");
            updateProduct(updates, product, userId);
        } else {
            createProduct(updates, userId);
        }
    }

    @SneakyThrows
    private void createProduct(Map<String, Object> updates, Long userId) {
        log.info(TAG + "Create product");
        Product entity = new Product();
        entity.setActive(true);
        entity.setCreatedBy(userRepo.getUser(userId));
        productRepo.save(entity);
        logService.addLog(userId, Action.CREATE, Section.PRODUCT, "Product was saved");
        updateProduct(updates, entity, userId);
    }

    @SneakyThrows
    private void updateProduct(Map<String, Object> updates, Product product, Long userId) throws ValueIsNotUnique {
        log.info(TAG + "Update product by user with id {}", userId);
        Long productId = (product.getId() != null) ? product.getId() : 0;
        updates.forEach((key, value) -> {
            switch (key) {
                case "active":
                    if (userRepo.getUser(userId).getRoles() == Role.ADMIN) {
                        product.setActive((Boolean) value);
                        product.setBarCode(null);
                        product.setRfidCode(null);
                        product.setInventoryNumber(null);
                        product.setSerialNumber(null);
                        addHistoryToProduct(userId, productId, Activity.VISIBILITY);
                    }
                    break;
                case "title":
                    product.setTitle((String) value);
                    addHistoryToProduct(userId, productId, Activity.TITLE);
                    break;
                case "description":
                    product.setDescription((String) value);
                    break;
                case "price":
                    product.setPrice(((Number) value).doubleValue());
                    addHistoryToProduct(userId, productId, Activity.PRICE);
                    break;
                case "bar_code":
                    if (productRepo.existsByBarCode((String) value)) {
                        throw new ValueIsNotUnique("Bar code " + value + " is taken");
                    } else {
                        product.setBarCode((String) value);
                        addHistoryToProduct(userId, productId, Activity.BAR_CODE);
                    }
                    break;
                case "rfid_code":
                    if (productRepo.existsByRfidCode((String) value)) {
                        throw new ValueIsNotUnique("Rfid code " + value + "is taken");
                    } else {
                        product.setRfidCode((String) value);
                        addHistoryToProduct(userId, productId, Activity.BAR_CODE);
                    }
                    break;
                case "inventory_number":
                    if (productRepo.existsByInventoryNumber((String) value)) {
                        throw new ValueIsNotUnique("Inventory number " + value + "is taken");
                    } else {
                        product.setInventoryNumber((String) value);
                        addHistoryToProduct(userId, productId, Activity.BAR_CODE);
                    }
                    break;
                case "serial_number":
                    if (productRepo.existsBySerialNumber((String) value)) {
                        throw new ValueIsNotUnique("Serial number " + value + "is taken");
                    } else {
                        product.setSerialNumber((String) value);
                        addHistoryToProduct(userId, productId, Activity.BAR_CODE);
                    }
                    break;
                case "liable_id":
                    product.setLiable(userRepo.findById(((Number) value).longValue()).orElseThrow(
                            () -> new ResourceNotFoundException("Liable person with id " + value + " not found")
                    ));
                    addHistoryToProduct(userId, productId, Activity.LIABLE);
                    break;
                case "receiver":
                    product.setReceiver((String) value);
                    addHistoryToProduct(userId, productId, Activity.RECEIVER);
                    break;
                case "kst_id":
                    product.setKst(configureService.getKSTById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.KST);
                    break;
                case "asset_status_id":
                    product.setAssetStatus(configureService.getAssetStatusById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.ASSET_STATUS);
                    break;
                case "unit_id":
                    product.setUnit(configureService.getUnitById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.UNIT);
                    break;
                case "branch_id":
//                    resolveIssueWithInventory(product, locationService.getBranchById(((Number) value).longValue()));
                    product.setBranch(locationService.getBranchById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.BRANCH);
                    break;
                case "location_id":
                    product.setLocation(locationService.getLocationById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.LOCATION);
                    break;
                case "mpk_id":
                    product.setMpk(configureService.getMPKById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.MPK);
                    break;
                case "type_id":
                    product.setType(typeService.getTypeById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.TYPE);
                    break;
                case "subtype_id":
                    product.setSubtype(typeService.getSubtypeById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.SUBTYPE);
                    break;
                case "producer":
                    product.setProducer((String) value);
                    addHistoryToProduct(userId, productId, Activity.PRODUCER);
                    break;
                case "supplier":
                    product.setSupplier((String) value);
                    addHistoryToProduct(userId, productId, Activity.SUPPLIER);
                    break;
                case "scrapping":
                    product.setScrapping((Boolean) value);
                    addHistoryToProduct(userId, productId, Activity.SCRAPPING);
                    break;
                case "scrapping_date":
                    product.setScrappingDate(LocalDate.parse((String) value));
                    addHistoryToProduct(userId, productId, Activity.SCRAPPING);
                    break;
                case "scrapping_reason":
                    product.setScrappingReason((String) value);
                    addHistoryToProduct(userId, productId, Activity.SCRAPPING);
                    break;
                case "document":
                    product.setDocument((String) value);
                    addHistoryToProduct(userId, productId, Activity.DOCUMENT);
                    break;
                case "document_date":
                    product.setDocumentDate(LocalDate.parse((String) value));
                    addHistoryToProduct(userId, productId, Activity.DOCUMENT_DATE);
                    break;
                case "warranty_period":
                    product.setWarrantyPeriod(LocalDate.parse((String) value));
                    addHistoryToProduct(userId, productId, Activity.WARRANTY_PERIOD);
                    break;
                case "inspection_date":
                    product.setInspectionDate(LocalDate.parse((String) value));
                    addHistoryToProduct(userId, productId, Activity.INSPECTION_DATE);
                    break;
                case "longitude":
                    product.setLongitude((Double) value);
                    addHistoryToProduct(userId, productId, Activity.GPS);
                    break;
                case "latitude":
                    product.setLatitude((Double) value);
                    addHistoryToProduct(userId, productId, Activity.GPS);
                    break;
                default:
                    break;
            }
        });
        productRepo.save(product);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, "Product was saved");
    }


    @Override
    @Modifying
    @SneakyThrows
    public List<ProductHistoryDto> getHistoryOfProductById(Long productId) {
        log.info(TAG + "Get history of product with id {}", productId);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " was not found"));
        return productHistoryRepo.findAllByProductOrderByCreatedDesc(product)
                .stream()
                .map(ProductMapper::toProductHistoryDto)
                .toList();
    }

    @SneakyThrows
    public void addHistoryToProduct(Long userId, Long productId, Activity activity) {
        ProductHistory productHistory = new ProductHistory();
        productHistory.setActivity(activity);
        productHistory.setCreated(LocalDateTime.now());
        productHistory.setProduct(productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found")));
        productHistory.setUser(userRepo.getUser(userId));
        productHistoryRepo.save(productHistory);
    }


    @SneakyThrows
    public void moveProduct(Product product, Branch branch, Location location, Long userId) {
        product.setBranch(branch);
        product.setLocation(location);
        productRepo.save(product);
        addHistoryToProduct(userId, product.getId(), Activity.LOCATION);
    }

    @SneakyThrows
    public void save(Product product) {
        productRepo.save(product);
    }
}
