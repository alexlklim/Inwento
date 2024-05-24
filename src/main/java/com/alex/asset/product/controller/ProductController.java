package com.alex.asset.product.controller;


import com.alex.asset.product.dto.ProductHistoryDto;
import com.alex.asset.product.service.ProductService;
import com.alex.asset.utils.SecHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product Controller", description = "Product API")
public class ProductController {
    private final String TAG = "PRODUCT_CONTROLLER - ";

    private final ProductService productService;


    @Operation(summary = "Get list of products with fields, mode:admin/emp  scrap:true/false")
    @GetMapping("/all/{mode}/{scrap}")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getAllProductsWithCustomFields(
            @PathVariable("mode") String mode,
            @PathVariable("scrap") Boolean isScrap,
            @RequestBody(required = false) List<String> productFields) {
        log.info(TAG + "Try to get all products with custom or all fields");
        return productService.getAllProducts(mode, isScrap, productFields, SecHolder.getUserId());
    }

    @Operation(summary = "Get list of products with fields, mode:admin/emp  scrap:true/false")
    @PostMapping("/all/{mode}/{scrap}")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getAllProductsWithCustomFieldsApp(
            @PathVariable("mode") String mode,
            @PathVariable("scrap") Boolean isScrap,
            @RequestBody(required = false) List<String> productFields) {
        log.info(TAG + "Try to get all products with custom or all fields");
        return productService.getAllProducts(mode, isScrap, productFields, SecHolder.getUserId());
    }


    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getById(
            @PathVariable("id") Long id,
            @RequestBody(required = false) List<String> productFields) {
        log.info(TAG + "Try to get product by id {}", id);
        return productService.getById(productFields, id, SecHolder.getUserId());
    }


    @Operation(summary = "Get product by bar code / rfid code / inventory number / serial number " +
            "(write null if you don't want to specify something" +
            "all this values are unique, server can return only one value" +
            "also add fields you want to get in the body of request")
    @GetMapping("/filter/unique/{bar_code}/{rfid_code}/{inventory_number}/{serial_number}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getProductByUniqueVales(
            @PathVariable(name = "bar_code", required = false) String barCode,
            @PathVariable(name = "rfid_code", required = false) String rfidCode,
            @PathVariable(name = "inventory_number", required = false) String inventoryNumber,
            @PathVariable(name = "serial_number", required = false) String serialNumber,
            @RequestBody(required = false) List<String> productFields) {
        log.info(TAG + "Try to get product by unique values {} / {} / {} / {}", barCode, rfidCode, inventoryNumber, serialNumber);
        return productService.getByUniqueValues(
                barCode, rfidCode, inventoryNumber, serialNumber,
                productFields, SecHolder.getUserId());
    }

    @Operation(summary = "Get products by title/bar code/rfid code")
    @GetMapping("/filter/{key_word}")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getProductsByTitleOrBarCode(
            @PathVariable("key_word") String keyWord,
            @RequestBody(required = false) List<String> productFields) {
        log.info(TAG + "Try to get all products by key word {}", keyWord);
        return productService.getByValue(keyWord, productFields);
    }

    @Operation(summary = "Get products in a special range (warranty period)")
    @GetMapping("/filter/warranty_period/{start_date}/{end_date}")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getProductsByWarrantyPeriod(
            @PathVariable("start_date") String startDate,
            @PathVariable("end_date") String endDate) {
        log.info(TAG + "Get products in a special range (warranty period) ");
        return productService.getByWarrantyPeriod(startDate, endDate);
    }

    @Operation(summary = "Get products in a special range (inspection period)")
    @GetMapping("/filter/inspection_period/{start_date}/{end_date}")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getProductsByInspectionPeriod(
            @PathVariable("start_date") String startDate,
            @PathVariable("end_date") String endDate) {
        log.info(TAG + "Get products in a special range (inspection period) ");
        return productService.getByInspectionPeriod(startDate, endDate);
    }

    @Operation(summary = "Update or create product")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody Map<String, Object> updates) {
        log.info(TAG + "Update product");
        productService.update(updates, SecHolder.getUserId());
    }

    @Operation(summary = "Get product history by id")
    @GetMapping("/history/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductHistoryDto> getHistoryOfProductById(
            @PathVariable("id") Long productId) {
        log.info(TAG + "Get history of product");
        return productService.getHistoryOfProductById(productId);
    }

}
