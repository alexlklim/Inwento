package com.alex.asset.data.migration.service;

import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.LocationService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.data.migration.dto.Asset;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.domain.User;
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


    @SneakyThrows
    public List<Asset> parseExcel(File tempFile, int lastRow, Long userId) throws ObjectAlreadyExistException {
        log.info(TAG + "Parse excel to asset objects and return list of them");
        List<Asset> assets = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(tempFile);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 2; i < lastRow; i++) {
                System.out.println(i);
                Row row = sheet.getRow(i);
                if (row == null) {
                    break;
                }
                Asset asset = new Asset();
                asset.setId(i);
                asset.setTitle(getStringValue(row.getCell(1)));
                asset.setDescription(getStringValue(row.getCell(2)));
                asset.setPrice(getNumericValue(row.getCell(3)));

                if (row.getCell(6) != null){
                    if (!productRepo.existsByBarCode(getStringValue(row.getCell(6)))){
                        asset.setBarCode(getStringValue(row.getCell(6)));
                    }
                }

                if (row.getCell(7) != null){
                    if (!productRepo.existsByRfidCode(getStringValue(row.getCell(7)))){
                        asset.setRfidCode(getStringValue(row.getCell(7)));
                    }
                }

                if (row.getCell(8) != null){
                    if (!productRepo.existsByInventoryNumber(getStringValue(row.getCell(8)))){
                        asset.setInventoryNumber(getStringValue(row.getCell(8)));
                    }
                }

                if (row.getCell(9) != null){
                    if (!productRepo.existsBySerialNumber(getStringValue(row.getCell(9)))){
                        asset.setSerialNumber(getStringValue(row.getCell(9)));
                    }
                }

                asset.setCreatedByEmail(userRepo.getUser(userId).getEmail());
                if (row.getCell(10) != null){
                    asset.setLiableEmail(Objects.requireNonNull(userRepo.getUserByEmail(getStringValue(row.getCell(10))).orElse(null)).getEmail());
                }
                if (row.getCell(11) != null){
                    asset.setReceiver(getStringValue(row.getCell(11)));
                }
                asset.setReceiver(getStringValue(row.getCell(11)));

                if (row.getCell(12) != null){
                    asset.setKst(configureService.getKSTByNum(getStringValue(row.getCell(12))));
                }
                if (row.getCell(13) != null){
                    asset.setAssetStatus(configureService.getAssetStatusByAssetStatus(getStringValue(row.getCell(13))));
                }
                if (row.getCell(14) != null){
                    asset.setUnit(configureService.getUnitByUnit(getStringValue(row.getCell(14))));
                }

                if (row.getCell(4) != null){
                    asset.setBranch(locationService.getBranchByBranch(getStringValue(row.getCell(4)), userId));
                    if (row.getCell(5) != null){
                        asset.setLocation(locationService.getLocationByLocationAndBranch(
                                getStringValue(row.getCell(5)),
                                getStringValue(row.getCell(4)),
                                userId));
                    }
                }
                if (row.getCell(15) != null){
                    asset.setMpk(configureService.getMPKByMPK(getStringValue(row.getCell(15)), userId));
                }
                if (row.getCell(18) != null){
                    asset.setProducer(getStringValue(row.getCell(18)));
                }
                if (row.getCell(19) != null){
                    asset.setSupplier(getStringValue(row.getCell(19)));
                }


                asset.setDocument(getStringValue(row.getCell(20)));
                asset.setDocumentDate(getLocalDateValue(row.getCell(21)));
                asset.setWarrantyPeriod(getLocalDateValue(row.getCell(22)));
                asset.setInspectionDate(getLocalDateValue(row.getCell(23)));
                assets.add(asset);
                System.out.println(asset);
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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

    public void saveAssets(List<Asset> assetList, Long userId) {
        log.info(TAG + "Save assets by user with id");
        User user = userRepo.getUser(userId);
        for (Asset asset : assetList) {
            productRepo.save(AssetMapper.toProduct(
                    asset,
                    user,
                    userRepo.findByEmail(asset.getLiableEmail()).orElse(null)));
        }
    }
}
