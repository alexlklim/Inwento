package com.alex.asset.data.migration.service;

import com.alex.asset.configure.domain.*;
import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Activity;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.exceptions.errors.user_error.ObjectAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelParser {

    private final String TAG = "DATA_MIGRATION_CONTROLLER - ";
    private final ConfigureService configureService;
    private final TypeService typeService;
    private final LocationService locationService;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final LogService logService;


    @SneakyThrows
    public List<Product> parseExcel(File tempFile, int lastRow, Long userId) throws IllegalStateException {
        log.info(TAG + "Parse excel to asset objects and return list of them");
        List<Product> assets = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(tempFile);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 2; i < lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) break;
                Product asset = new Product();
                if (Objects.equals(getStringValue(row.getCell(1)), "0.0") && row.getCell(1) != null) continue;
                else asset.setTitle(getStringValue(row.getCell(1)));

                if (!Objects.equals(getStringValue(row.getCell(2)), "0.0") && row.getCell(2) != null)
                    asset.setDescription(getStringValue(row.getCell(2)));

                if (!Objects.equals(getStringValue(row.getCell(3)), "0.0") && row.getCell(3) != null)
                    asset.setPrice(getNumericValue(row.getCell(3)));

                if (!Objects.equals(getStringValue(row.getCell(4)), "0.0") && row.getCell(4) != null) {
                    asset.setBranch(locationService.getBranchByBranch(getStringValue(row.getCell(4)), userId));
                    if (!Objects.equals(getStringValue(row.getCell(5)), "0.0") && row.getCell(5) != null) {
                        asset.setLocation(locationService.getLocationByLocationAndBranch(
                                getStringValue(row.getCell(5)),
                                getStringValue(row.getCell(4)),
                                userId));
                    }
                }

                if (row.getCell(6) != null) {
                    String barcode;
                    Cell cell = row.getCell(6);
                    if (cell.getCellType() == CellType.STRING) {
                        barcode = cell.getStringCellValue().trim();
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        double numericValue = cell.getNumericCellValue();
                        barcode = String.valueOf((long) numericValue);
                    } else barcode = "";
                    if (!barcode.equals("0.0") && !productRepo.existsByBarCode(barcode) && !Product.containsProductWithBarcode(assets, barcode)) {
                        asset.setBarCode(barcode);
                    }
                }



                if (!Objects.equals(getStringValue(row.getCell(7)), "0.0") && row.getCell(7) != null) {
                    String rfidCode = getStringValue(row.getCell(7));
                    if (!productRepo.existsByRfidCode(rfidCode) && !Product.containsProductWithRfidCode(assets, rfidCode)) {
                        asset.setRfidCode(rfidCode);
                    }
                }

                if (!Objects.equals(getStringValue(row.getCell(8)), "0.0") && row.getCell(8) != null) {
                    String inventoryNumber = getStringValue(row.getCell(8));
                    if (!productRepo.existsByInventoryNumber(inventoryNumber)
                            && !Product.containsProductWithInventoryNumber(assets, inventoryNumber)) {
                        asset.setInventoryNumber(inventoryNumber);
                    }
                }
                if (!Objects.equals(getStringValue(row.getCell(9)), "0.0") && row.getCell(9) != null) {
                    String serialNumber = getStringValue(row.getCell(9));
                    if (!productRepo.existsBySerialNumber(serialNumber) && !Product.containsProductWithSerialNumber(assets, serialNumber)) {
                        asset.setSerialNumber(serialNumber);
                    }
                }


                if (!Objects.equals(getStringValue(row.getCell(10)), "0.0") && row.getCell(10) != null) {
                    userRepo.findByEmail(getStringValue(row.getCell(10)))
                            .ifPresent(liable -> {
                                asset.setLiable(liable);
                                asset.setUserLiableId(liable.getId());
                                asset.setUserLiableName(liable.getFirstname() + " " + liable.getLastname());
                            });
                }

                if (!Objects.equals(getStringValue(row.getCell(11)), "0.0") && row.getCell(11) != null)
                    asset.setReceiver(getStringValue(row.getCell(11)));

                if (!Objects.equals(getStringValue(row.getCell(12)), "0.0") && row.getCell(12) != null) {
                    KST kst = configureService.getKSTByNum(getStringValue(row.getCell(12)));
                    if (kst != null) asset.setKst(kst);
                }
                if (!Objects.equals(getStringValue(row.getCell(13)), "0.0") && row.getCell(13) != null) {
                    AssetStatus assetStatus = configureService.getAssetStatusByAssetStatus(getStringValue(row.getCell(13)));
                    if (assetStatus != null) asset.setAssetStatus(assetStatus);
                }
                if (!Objects.equals(getStringValue(row.getCell(14)), "0.0") && row.getCell(14) != null) {
                    Unit unit = configureService.getUnitByUnit(getStringValue(row.getCell(14)));
                    if (unit != null) asset.setUnit(unit);
                }

                if (!Objects.equals(getStringValue(row.getCell(15)), "0.0") && row.getCell(15) != null) {
                    MPK mpk = configureService.getMPKByMPK(getStringValue(row.getCell(15)), userId);
                    if (mpk != null) asset.setMpk(mpk);
                }
                if (!Objects.equals(getStringValue(row.getCell(16)), "0.0") && row.getCell(16) != null) {
                    Type type = typeService.getTypeByType(getStringValue(row.getCell(16)), userId);
                    asset.setType(type);
                    if (!Objects.equals(getStringValue(row.getCell(17)), "0.0") && row.getCell(17) != null) {
                        Subtype subtype = typeService.getSubtypeBySubtypeAndType(type, getStringValue(row.getCell(17)), userId);
                        if (subtype != null) asset.setSubtype(subtype);
                    }
                }

                if (!Objects.equals(getStringValue(row.getCell(18)), "0.0") && row.getCell(18) != null)
                    asset.setProducer(getStringValue(row.getCell(18)));

                if (!Objects.equals(getStringValue(row.getCell(19)), "0.0") && row.getCell(19) != null)
                    asset.setSupplier(getStringValue(row.getCell(19)));

                if (!Objects.equals(getStringValue(row.getCell(20)), "0.0") && row.getCell(20) != null)
                    asset.setDocument(getStringValue(row.getCell(20)));

                if (!Objects.equals(getStringValue(row.getCell(21)), "0.0") && row.getCell(21) != null)
                    asset.setDocumentDate(getLocalDateValue(row.getCell(21)));

                if (!Objects.equals(getStringValue(row.getCell(22)), "0.0") && row.getCell(22) != null)
                    asset.setWarrantyPeriod(getLocalDateValue(row.getCell(22)));

                if (!Objects.equals(getStringValue(row.getCell(23)), "0.0") && row.getCell(23) != null)
                    asset.setInspectionDate(getLocalDateValue(row.getCell(23)));

                assets.add(asset);
            }
            workbook.close();
        } catch (IOException | ObjectAlreadyExistException | IllegalStateException e) {
            log.error(TAG + "Converted MultipartFile to File");
        }
        return assets;
    }

    private String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        } else if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else {
            return String.valueOf(cell.getNumericCellValue());
        }
    }

    private Double getNumericValue(Cell cell) {
        if (cell == null) {
            return null;
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else {
            return null;
        }
    }

    private LocalDate getLocalDateValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        DataFormatter dataFormatter = new DataFormatter();
        String dateString = dataFormatter.formatCellValue(cell);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

        return LocalDate.parse(dateString, dateFormatter);
    }

    public File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        log.info(TAG + "Converted MultipartFile to File");
        File tempFile = File.createTempFile("temp", null);
        try (OutputStream os = new FileOutputStream(tempFile)) {
            os.write(file.getBytes());
        }
        return tempFile;
    }


    private final ProductService productService;

    public int saveAssets(List<Product> assetList, Long userId) {
        log.info(TAG + "Save assets by user with id");
        int amountOfSavedProduct = 0;

        for (Product product : assetList) {
            log.info(TAG + "Save asset with bar code: " + product.getBarCode());
            product.setCreatedBy(userRepo.getUser(userId));
            product.setActive(true);
            product.setLiable(userRepo.findById(product.getUserLiableId()).orElse(null));
            Product productFromDb = productRepo.save(product);
            logService.addLog(userId, Action.CREATE, Section.PRODUCT, product.getTitle());
            productService.addHistoryToProduct(userId, productFromDb.getId(), Activity.PRODUCT_WAS_CREATED);
            amountOfSavedProduct++;
        }
        return amountOfSavedProduct;
    }
}
