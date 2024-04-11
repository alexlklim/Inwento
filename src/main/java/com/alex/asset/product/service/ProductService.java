package com.alex.asset.product.service;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Activity;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.dto.ProductV1Dto;
import com.alex.asset.product.dto.ProductV3Dto;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductHistoryRepo;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final InventoryRepo inventoryRepo;
    private final EventRepo eventRepo;
    private final ConfigureService configureService;
    private final TypeService typeService;


    public List<ProductV1Dto> getAll() {
        log.info(TAG + "get all products");
        return productRepo.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<ProductV1Dto> getActive() {
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


    public ProductV1Dto create(ProductV1Dto dto, Long userId) {
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
    public ProductV1Dto getById(Long productId) {
        log.info(TAG + "get product by id");
        return productMapper.toDto(productRepo.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + productId + " not found"))
        );

    }

    public ProductV3Dto getShortProductById(Long productId) {
        return convertProductToProductV3Dto(
                productRepo.findById(productId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Product with id " + productId + " not found")
                        )
        );
    }


    private ProductV3Dto convertProductToProductV3Dto(Product product) {
        ProductV3Dto dto = new ProductV3Dto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setBarCode(product.getBarCode());
        dto.setReceiver(product.getReceiver());
        dto.setBranch(product.getBranch().getBranch());
        dto.setLiable(product.getLiable().getFirstname() + " " + product.getLiable().getLastname());
        return dto;
    }


    public Map<Long, String> getByTitleOrBarCode(String title) {
        log.info(TAG + "get product by title");
        return productRepo.getByTitleOrBarCode(title)
                .stream()
                .collect(Collectors.toMap(Product::getId, Product::getTitle));

    }

    public ProductV3Dto getProductByBarCode(String barCode) {
        return getShortProductById(
                productRepo.getProductByBarCode(barCode).orElseThrow(
                        () -> new ResourceNotFoundException("Product with bar code " + barCode + " not found")
                ).getId()
        );
    }

    public List<ProductV3Dto> getListOfProductV3DTO() {
        return productRepo.getActive()
                .stream()
                .map(this::convertProductToProductV3Dto)
                .collect(Collectors.toList());

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


    @SneakyThrows
    public void updateProduct(Long userId, Map<String, Object> updates) {
        log.info(TAG + "Update product by user with id {}", userId);
        Long productId = ((Number) updates.getOrDefault("id", null)).longValue();
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found"));
        updates.forEach((key, value) -> {
            switch (key) {
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
                    product.setBarCode((String) value);
                    addHistoryToProduct(userId, productId, Activity.BAR_CODE);
                    break;
                case "rfid_code":
                    product.setRfidCode((String) value);
                    addHistoryToProduct(userId, productId, Activity.RFID_CODE);
                    break;
                case "inventory_number":
                    product.setInventoryNumber((String) value);
                    addHistoryToProduct(userId, productId, Activity.INVENTORY_NUMBER);
                    break;
                case "serial_number":
                    product.setSerialNumber((String) value);
                    addHistoryToProduct(userId, productId, Activity.SERIAL_NUMBER);
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
                    resolveIssueWithInventory(product, configureService.getBranchById(((Number) value).longValue()));
                    product.setBranch(configureService.getBranchById(((Number) value).longValue()));
                    addHistoryToProduct(userId, productId, Activity.BRANCH);
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
            }
        });

        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, "Product was changed");
        product.setActive(true);
        productRepo.save(product);

    }

    public void resolveIssueWithInventory(Product product, Branch branchById) {
        log.info(TAG + "Resolve issue with inventory for branch {}", branchById);
        Inventory inventory = inventoryRepo.getCurrentInvent(LocalDate.now()).orElse(null);
        if (inventory == null) return;
        Event event = eventRepo.findByProductId(product.getId(), inventory).orElse(null);
        if (event == null) return;
        event.getProducts().remove(product);
    }

    @SneakyThrows
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
