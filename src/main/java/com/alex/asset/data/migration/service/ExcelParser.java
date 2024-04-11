package com.alex.asset.data.migration.service;

import com.alex.asset.configure.services.ConfigureService;
import com.alex.asset.configure.services.TypeService;
import com.alex.asset.data.migration.dto.Asset;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelParser {

    private final String TAG = "DATA_MIGRATION_CONTROLLER - ";
    private final UserRepo userRepo;
    private final ConfigureService configureService;
    private final TypeService typeService;


    @SneakyThrows
    public List<Asset> parseExcel(File tempFile, Long userId) {
        List<Asset> assets = new ArrayList<>();
        FileInputStream fis = new FileInputStream(tempFile);
        try {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // Assuming first sheet

            for (int i = 2; i < sheet.getPhysicalNumberOfRows()+1; i++) {
                Row row = sheet.getRow(i);
                Asset asset = new Asset();
                asset.setTitle(getStringValue(row.getCell(1)));
                asset.setDescription(getStringValue(row.getCell(2)));
                asset.setPrice(getNumericValue(row.getCell(3)));

                asset.setBarCode(getStringValue(row.getCell(5)));
                asset.setRfidCode(getStringValue(row.getCell(6)));
                asset.setInventoryNumber(getStringValue(row.getCell(7)));
                asset.setSerialNumber(getStringValue(row.getCell(8)));

                asset.setCreatedBy(userRepo.getUser(userId));
                asset.setLiable(userRepo.getUserByEmail(getStringValue(row.getCell(9))).orElse(null));
                asset.setReceiver(getStringValue(row.getCell(10)));

                asset.setKst(configureService.getKSTByNum(getStringValue(row.getCell(11))));
                asset.setAssetStatus(configureService.getAssetStatusByAssetStatus(getStringValue(row.getCell(12))));
                asset.setUnit(configureService.getUnitByUnit(getStringValue(row.getCell(13))));

                asset.setBranch(configureService.getBranchByBranch(getStringValue(row.getCell(4)), userId));
                asset.setMpk(configureService.getMPKByMPK(getStringValue(row.getCell(14)), userId));

                asset.setType(typeService.getTypeByType(getStringValue(row.getCell(15)), userId));
                asset.setSubtype(typeService.getSubtypeBySubtypeAndType(
                        getStringValue(row.getCell(16)),
                        getStringValue(row.getCell(14)),
                        userId));

                asset.setProducer(getStringValue(row.getCell(17)));
                asset.setSupplier(getStringValue(row.getCell(18)));

                asset.setDocument(getStringValue(row.getCell(19)));
                asset.setDocumentDate(getLocalDateValue(row.getCell(20)));
                asset.setWarrantyPeriod(getLocalDateValue(row.getCell(21)));
                asset.setInspectionDate(getLocalDateValue(row.getCell(22)));
                assets.add(asset);
                System.out.println(asset);
            }
            workbook.close();
        } catch (IOException e) {
            throw new IOException("Something Wrong");
        } finally {
            fis.close();
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(dateString, dateFormatter);
    }

    public File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        try (OutputStream os = new FileOutputStream(tempFile)) {
            os.write(file.getBytes());
        }
        return tempFile;
    }
}
