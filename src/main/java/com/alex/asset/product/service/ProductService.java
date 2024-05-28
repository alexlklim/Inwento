package com.alex.asset.product.service;


import com.alex.asset.comments.Comment;
import com.alex.asset.comments.CommentService;
import com.alex.asset.company.domain.Company;
import com.alex.asset.company.service.CompanyRepo;
import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.exceptions.product.IdNotProvidedException;
import com.alex.asset.exceptions.product.LengthOfCodeNotConfigured;
import com.alex.asset.exceptions.product.ValueIsNotUnique;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Activity;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.domain.ProductHistory;
import com.alex.asset.product.dto.ProductCodesDTO;
import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductHistoryRepo;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.Utils;
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
    private final ConfigureService configureService;
    private final TypeService typeService;
    private final LocationService locationService;
    private final ProductRepo productRepo;
    private final ProductHistoryRepo productHistoryRepo;
    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final InventoryRepo inventoryRepo;
    private final ScannedProductRepo scannedProductRepo;
    private final CompanyRepo companyRepo;


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
            if (Role.ADMIN == userRepo.getUser(userId).getRoles()) {
                products = productRepo.getProductsListByAdmin(true, isScrap);
            }
        } else products = productRepo.getProductsListByEmp(isScrap);

        if (productFields == null || productFields.isEmpty()) productFields = Utils.PRODUCT_FIELDS;
        if (products == null) return Collections.emptyList();
        return getProductDTOs(products, productFields);
    }

    List<Map<String, Object>> getProductDTOs(List<Product> products, List<String> productFields) {
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
        } else throw new ResourceNotFoundException("Product not found with this code ");
    }


    @Override
    @SneakyThrows
    public List<Map<String, Object>> getByValue(String keyWord, Boolean isScrapped, List<String> productFields) {
        log.info(TAG + "get product by title");
        if (productFields == null || productFields.isEmpty()) productFields = Utils.PRODUCT_FIELDS;
        return getProductDTOs(productRepo.getByKeyWord(keyWord, isScrapped), productFields);
    }

    @Override
    public List<Map<String, Object>> getByWarrantyPeriod(String startDate, String endDate) {
        return getProductDTOs(
                productRepo.getProductsByWarrantyPeriod(
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate)
                ),
                Utils.PRODUCT_FIELDS);

    }


    @Override
    public List<Map<String, Object>> getByInspectionPeriod(String startDate, String endDate) {
        return getProductDTOs(
                productRepo.getProductsByInspectionPeriod(
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate)
                ),
                Utils.PRODUCT_FIELDS);

    }


    @Override
    @Modifying
    @SneakyThrows
    public ProductCodesDTO update(Map<String, Object> updates, Long userId) {
        log.info(TAG + "Update or create product");
        if (updates.containsKey("id")) {
            Long productId = ((Number) updates.get("id")).longValue();
            Product product = productRepo.findById(productId).orElseThrow(
                    () -> new ResourceNotFoundException("Product not found"));
            updates.remove("id");
            return updateProduct(updates, product, userId, false);
        } else {
            throw new IdNotProvidedException("Problem occurs with the id of product, Be sure you provide it");
        }
    }


    @SneakyThrows
    @Transactional
    public ProductCodesDTO createProduct(Map<String, Object> updates, Long userId) {
        log.info(TAG + "Create product");
        Company company = companyRepo.findAll().get(0);


        if (companyRepo.areNullOrZeroBarCodeLength(company)) {
            throw new LengthOfCodeNotConfigured("RFID or BAR code length are null or zero. Please configure it to continue");
        }


        Product entity = new Product();
        entity.setActive(true);
        entity.setCreatedBy(userRepo.getUser(userId));
        productRepo.save(entity);

        entity.setBarCode(generateBarCode(company, entity.getId()));
        entity.setRfidCode(generateRfidCode(company, entity.getId()));

        logService.addLog(userId, Action.CREATE, Section.PRODUCT, "Product was saved");
        return updateProduct(updates, entity, userId, true);
    }

    @SneakyThrows
    private String generateBarCode(Company company, Long productId) {
        log.info(TAG + "Generate Bar Code");
        String barCode =
                (company.getBarCodePrefix() != null ? company.getBarCodePrefix() : "") +
                (company.getBarCodeSuffix() != null ? company.getBarCodeSuffix() : "");

        // get the number of zeros in bar code
        int numberOfZeros = company.getBarCodeLength() - barCode.length() - Long.toString(productId).length();
        String zeros = String.format("%0" + numberOfZeros + "d", 0);
        return barCode + zeros + productId;
    }

    @SneakyThrows
    private String generateRfidCode(Company company, Long productId) {
        log.info(TAG + "Generate RFID Code");
        String rfidCode =
                (company.getRfidCodePrefix() != null ? company.getRfidCodePrefix() : "") +
                (company.getRfidCodeSuffix() != null ? company.getRfidCodeSuffix() : "");
        int numberOfZeros = company.getBarCodeLength() - rfidCode.length() - Long.toString(productId).length();
        String zeros = String.format("%0" + numberOfZeros + "d", 0);
        return rfidCode + zeros + productId;
    }

    @SneakyThrows
    private ProductCodesDTO updateProduct(Map<String, Object> updates, Product product, Long userId, boolean isCreated) throws ValueIsNotUnique {
        log.info(TAG + "Update product by user with id {}", userId);
        Long productId = (product.getId() != null) ? product.getId() : 0;
        updates.forEach((key, value) -> {
            switch (key) {
                case "active":
                    // if inventory is active, there is no possibility to delete product
                    if (inventoryRepo.getCurrentInventory(LocalDate.now()).orElse(null) != null) break;

                    if (userRepo.getUser(userId).getRoles() == Role.ADMIN) {
                        product.setActive((Boolean) value);
                        if (!product.isActive()) {
                            // delete all unique values for this product
                            product.setBarCode(null);
                            product.setRfidCode(null);
                            product.setInventoryNumber(null);
                            product.setSerialNumber(null);
                        }
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
                    Branch branch = locationService.getBranchById(((Number) value).longValue());
                    if (!isCreated){
                        resolveIssueWithInventory(
                                product,
                                branch,
                                userRepo.getUser(userId));
                    } else product.setBranch(branch);
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
                case "comments":
                    Comment comment = commentService.createComment((String) value, product, userRepo.getUser(userId));
                    product.getComments().add(comment);
                    addHistoryToProduct(userId, productId, Activity.COMMENT);
                    break;
                default:
                    break;
            }
        });
        productRepo.save(product);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, "Product was saved");


        return ProductCodesDTO.builder()
                .id(product.getId())
                .barCode(product.getBarCode())
                .rfidCode(product.getRfidCode())
                .build();
    }

    private final CommentService commentService;

    @SneakyThrows
    private void resolveIssueWithInventory(Product product, Branch branch, User user) {
        log.error(TAG + "Resolve issue with inventory");
        Inventory inventory = inventoryRepo.getCurrentInventory(LocalDate.now()).orElse(null);
        if (inventory != null) {
            Event eventToMove = eventRepo.findByInventoryAndBranch(inventory, branch).orElseThrow(
                    () -> new ResourceNotFoundException("Event not found by current inventory and branch")
            );
            moveProductByBranch(product, eventToMove, user);
        }
        product.setBranch(branch);
        log.error(TAG + "Inventory is not active, no needed to resolve issues with inventory");
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
        log.info(TAG + "Add history to products");
        ProductHistory productHistory = new ProductHistory().toBuilder()
                .activity(activity)
                .created(LocalDateTime.now())
                .product(productRepo.findById(productId).orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + productId + " not found")))
                .user(userRepo.getUser(userId))
                .build();
        productHistoryRepo.save(productHistory);
    }


    @SneakyThrows
    public void moveProductByLocation(Product product, Location location, Long userId) {
        log.info(TAG + "Move product by location");
        product.setLocation(location);
        productRepo.save(product);
        addHistoryToProduct(userId, product.getId(), Activity.LOCATION);
    }

    @SneakyThrows
    public void save(Product product) {
        productRepo.save(product);
    }


    @SneakyThrows
    public void moveProductByBranch(Product product, Event eventToMove, User user) {
        log.info(TAG + "Move product by Branch");
        // is inventory active or no
        // inventory active -> get scanned product by event and product
        // change the event in scanned_product
        // change the branch in product

        Inventory inventory = inventoryRepo.getCurrentInventory(LocalDate.now()).orElse(null);
        if (inventory != null) {
            Event oldEvent = eventRepo.findByInventoryAndBranch(inventory, product.getBranch()).orElseThrow(
                    () -> new ResourceNotFoundException("Event not found for current inventory and product branch"));
            ScannedProduct scannedProduct = scannedProductRepo.findByProductAndEvent(product, oldEvent).orElse(null);
            if (scannedProduct == null) {
                scannedProduct = new ScannedProduct(product, eventToMove, user, true);
            }
            scannedProduct.setEvent(eventToMove);
            scannedProductRepo.save(scannedProduct);
        }
        product.setBranch(eventToMove.getBranch());
        productRepo.save(product);
    }
}
