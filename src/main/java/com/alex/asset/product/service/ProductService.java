package com.alex.asset.product.service;


import com.alex.asset.company.domain.Company;
import com.alex.asset.company.service.CompanyRepo;
import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.domain.Location;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.services.BranchService;
import com.alex.asset.configure.services.services.MpkService;
import com.alex.asset.configure.services.services.TypeService;
import com.alex.asset.exceptions.product.ActionIsNotPossibleException;
import com.alex.asset.exceptions.product.IdNotProvidedException;
import com.alex.asset.exceptions.product.LengthOfCodeNotConfiguredException;
import com.alex.asset.exceptions.product.ValueIsNotUniqueException;
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
import com.alex.asset.product.domain.Comment;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.mappers.ProductMapper;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dictionaries.UtilProduct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final String TAG = "PRODUCT_SERVICE - ";
    
    private final LogService logService;
    private final ConfigureService configureService;
    private final CommentService commentService;
    private final TypeService typeService;
    private final BranchService branchService;
    private final MpkService mpkService;
    private final ProductHistoryService productHistoryService;

    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final InventoryRepo inventoryRepo;
    private final ScannedProductRepo scannedProductRepo;
    private final CompanyRepo companyRepo;


    @Modifying
    @SneakyThrows
    public Map<String, Object> update(Map<String, Object> updates, Long userId) {
        log.info(TAG + "Update or create product");
        if (updates.containsKey(UtilProduct.ID)) {
            Product product = productRepo.findById(
                            ((Number) updates.get(UtilProduct.ID)).longValue())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Product not found"));
            updates.remove(UtilProduct.ID);
            return updateProduct(updates, product, userId, false);
        } else {
            throw new IdNotProvidedException("Problem occurs with the id of product, Be sure you provide it");
        }
    }


    @SneakyThrows
    @Transactional
    public Map<String, Object> createProduct(Map<String, Object> updates, Long userId) {
        log.info(TAG + "Create product");
        Company company = companyRepo.findAll().get(0);
        if (companyRepo.areNullOrZeroBarCodeLength(company)) {
            throw new LengthOfCodeNotConfiguredException("RFID or BAR code length are null or zero. Please configure it to continue");
        }


        Product entity = new Product();
        entity.setActive(true);
        entity.setCreatedBy(userRepo.getUser(userId));
        entity.setProductHistories(new ArrayList<>());
        entity.setComments(new ArrayList<>());
        productRepo.save(entity);

        entity.setBarCode(generateBarCode(company, entity.getId()));
        entity.setRfidCode(generateRfidCode(company, entity.getId()));

        logService.addLog(userId, Action.CREATE, Section.PRODUCT, "Product was saved");
        updates.remove(UtilProduct.ACTIVE);
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
    private Map<String, Object> updateProduct(Map<String, Object> updates, Product product, Long userId, boolean isCreated) throws ValueIsNotUniqueException {
        log.info(TAG + "Update product by user with id {}", userId);
        User user = userRepo.getUser(userId);
        updates.forEach((key, value) -> {
            switch (key) {
                case UtilProduct.ACTIVE:
                    // if inventory is active, there is no possibility to delete product
                    if (inventoryRepo.getCurrentInventory(LocalDate.now()).orElse(null) != null)
                        throw new ActionIsNotPossibleException("There is not possibility to delete product during active inventory");

                    if (userRepo.getUser(userId).getRoles() == Role.ADMIN) {
                        product.setActive((Boolean) value);
                        if (!product.isActive()) {
                            // delete all unique values for this product
                            product.setBarCode(null);
                            product.setRfidCode(null);
                            product.setInventoryNumber(null);
                            product.setSerialNumber(null);
                        }
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.VISIBILITY));
                    }
                    break;
                case UtilProduct.TITLE:
                    product.setTitle((String) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.TITLE));
                    break;
                case UtilProduct.DESCRIPTION:
                    product.setDescription((String) value);
                    break;
                case UtilProduct.PRICE:
                    product.setPrice(((Number) value).doubleValue());
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.PRICE));
                    break;
                case UtilProduct.BAR_CODE:
                    if (productRepo.existsByBarCode((String) value)) {
                        throw new ValueIsNotUniqueException("Bar code " + value + " is taken");
                    } else {
                        product.setBarCode((String) value);
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.BAR_CODE));
                    }
                    break;
                case UtilProduct.RFID_CODE:
                    if (productRepo.existsByRfidCode((String) value)) {
                        throw new ValueIsNotUniqueException("Rfid code " + value + "is taken");
                    } else {
                        product.setRfidCode((String) value);
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.RFID_CODE));
                    }
                    break;
                case UtilProduct.INVENTORY_NUMBER:
                    if (productRepo.existsByInventoryNumber((String) value)) {
                        throw new ValueIsNotUniqueException("Inventory number " + value + "is taken");
                    } else {
                        product.setInventoryNumber((String) value);
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.INVENTORY_NUMBER));
                    }
                    break;
                case UtilProduct.SERIAL_NUMBER:
                    if (productRepo.existsBySerialNumber((String) value)) {
                        throw new ValueIsNotUniqueException("Serial number " + value + "is taken");
                    } else {
                        product.setSerialNumber((String) value);
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.SERIAL_NUMBER));
                    }
                    break;
                case UtilProduct.LIABLE_ID:
                    product.setLiable(userRepo.findById(((Number) value).longValue()).orElseThrow(
                            () -> new ResourceNotFoundException("Liable person with id " + value + " not found")
                    ));
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.LIABLE));
                    break;
                case UtilProduct.RECEIVER:
                    product.setReceiver((String) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.RECEIVER));
                    break;
                case UtilProduct.KST_ID:
                    product.setKst(configureService.getKSTById(((Number) value).longValue()));
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.KST));
                    break;
                case UtilProduct.ASSET_STATUS_ID:
                    product.setAssetStatus(configureService.getAssetStatusById(((Number) value).longValue()));
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.ASSET_STATUS));
                    break;
                case UtilProduct.UNIT_ID:
                    product.setUnit(configureService.getUnitById(((Number) value).longValue()));
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.UNIT));
                    break;
                case UtilProduct.BRANCH_ID:
                    Branch branch = branchService.getBranchById(((Number) value).longValue()).orElse(null);
                    if (!isCreated && branch != null)
                        resolveIssueWithInventory(product, branch, userRepo.getUser(userId));
                    else product.setBranch(branch);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.BRANCH));
                    break;
                case UtilProduct.LOCATION_ID:
                    branchService.getLocationById(((Number) value).longValue()).ifPresent(location -> {
                        product.setLocation(location);
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.LOCATION));
                    });
                    break;
                case UtilProduct.MPK_ID:
                    mpkService.getMPKById(((Number) value).longValue()).ifPresent(mpk -> {
                        product.setMpk(mpk);
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.MPK));
                    });
                    break;
                case UtilProduct.TYPE_ID:
                    typeService.getTypeById(((Number) value).longValue()).ifPresent(type -> {
                        product.setType(type);
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.TYPE));
                    });
                    break;
                case UtilProduct.SUBTYPE_ID:
                    typeService.getSubtypeById(((Number) value).longValue()).ifPresent(subtype -> {
                        product.setSubtype(subtype);
                        product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.SUBTYPE));
                    });
                    break;
                case UtilProduct.PRODUCER:
                    product.setProducer((String) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.PRODUCER));
                    break;
                case UtilProduct.SUPPLIER:
                    product.setSupplier((String) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.SUPPLIER));
                    break;
                case UtilProduct.SCRAPPING:
                    product.setScrapping((Boolean) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.SCRAPPING));
                    break;
                case UtilProduct.SCRAPPING_DATE:
                    product.setScrappingDate(LocalDate.parse((String) value));
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.SCRAPPING));
                    break;
                case UtilProduct.SCRAPPING_REASON:
                    product.setScrappingReason((String) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.SCRAPPING));
                    break;
                case UtilProduct.DOCUMENT:
                    product.setDocument((String) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.DOCUMENT));
                    break;
                case UtilProduct.DOCUMENT_DATE:
                    product.setDocumentDate(LocalDate.parse((String) value));
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.DOCUMENT_DATE));
                    break;
                case UtilProduct.WARRANTY_PERIOD:
                    product.setWarrantyPeriod(LocalDate.parse((String) value));
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.WARRANTY_PERIOD));
                    break;
                case UtilProduct.INSPECTION_DATE:
                    product.setInspectionDate(LocalDate.parse((String) value));
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.INSPECTION_DATE));
                    break;
                case UtilProduct.LONGITUDE:
                    product.setLongitude((Double) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.GPS));
                    break;
                case UtilProduct.LATITUDE:
                    product.setLatitude((Double) value);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.GPS));
                    break;
                case UtilProduct.COMMENTS:
                    Comment comment = commentService.createComment((String) value, product, userRepo.getUser(userId));
                    product.getComments().add(comment);
                    product.getProductHistories().add(productHistoryService.createProductHistory(user, product, Activity.COMMENT));
                    break;
                default:
                    break;
            }
        });
        productRepo.save(product);
        logService.addLog(userId, Action.UPDATE, Section.PRODUCT, "Product was saved");

        return ProductMapper.toDTOWithCustomFields(product, UtilProduct.getProductCodes());

    }


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

    

    @SneakyThrows
    public void moveProductByLocation(Product product, Location location, Long userId) {
        log.info(TAG + "Move product by location");
        product.setLocation(location);
        productRepo.save(product);
        product.getProductHistories().add(productHistoryService.createProductHistory(userId, product, Activity.LOCATION));
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