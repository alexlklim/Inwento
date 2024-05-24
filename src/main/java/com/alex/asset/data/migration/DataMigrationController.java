package com.alex.asset.data.migration;

import com.alex.asset.data.migration.service.ExcelParser;
import com.alex.asset.product.domain.Product;
import com.alex.asset.utils.SecHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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


    @Operation(summary = "Parse excel to products objects, provide which row should be the last")
    @PostMapping("/{last_row}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> parseExcelToProductObjects(
            @PathVariable("last_row") int lastRow,
            @RequestBody MultipartFile file) throws IOException {
        log.info(TAG + "Parse excel to product objects");
        return excelParser.parseExcel(
                excelParser.convertMultiPartFileToFile(file),
                lastRow,
                SecHolder.getUserId());
    }

    @Operation(summary = "Parse excel to products objects TEST")
    @PostMapping("/test/{last_row}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> parseExcelToProductObjectsTest(
            @PathVariable("last_row") int lastRow
    ) {
        log.info(TAG + "Parse excel to product objects TEST");
        return excelParser.parseExcel(
                new File("C:\\Folder\\server\\assets.xlsx"),
                lastRow,
                SecHolder.getUserId());
    }


    @Operation(summary = "Save received asset in DB")
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public int save(
            @RequestBody List<Product> assetList) {
        log.info(TAG + "Save assets in DB");
        return excelParser.saveAssets(assetList, SecHolder.getUserId());
    }


}
