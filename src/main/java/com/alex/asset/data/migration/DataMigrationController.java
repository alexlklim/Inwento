package com.alex.asset.data.migration;

import com.alex.asset.data.migration.dto.Asset;
import com.alex.asset.data.migration.service.ExcelParser;
import com.alex.asset.utils.SecHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/data/migration/excel")
@Tag(name = "Excel Converter Controller", description = "Excel converter API")
public class DataMigrationController {
    private final String TAG = "DATA_MIGRATION_CONTROLLER - ";
    private final ExcelParser excelParser;

    @SneakyThrows
    @Operation(summary = "Parse excel to products objects")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Asset> parseExcelToProductObjects(
            @RequestBody MultipartFile file) throws IOException {
        log.info(TAG + "Parse excel to product objects");
        return excelParser.parseExcel(
                excelParser.convertMultiPartFileToFile(file),
                SecHolder.getUserId());
    }

    @Operation(summary = "Parse excel to products objects TEST")
    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public List<Asset> parseExcelToProductObjectsTest() {
        log.info(TAG + "Parse excel to product objects TEST");
        return excelParser.parseExcel(
                new File("C:\\Folder\\CSMM\\Inventory_Product_List3.xlsx"),
                SecHolder.getUserId());
    }
}
