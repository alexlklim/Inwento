package com.alex.asset.utils;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.UnknownProduct;
import com.alex.asset.product.dto.ProductV2Dto;
import com.alex.asset.security.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public final static String[] PUBLIC_ROUTES = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh",
            "/api/v1/auth/pw/forgot",
            "/api/v1/auth/pw/recovery/**",

            "/api/core/get",
            "/swagger-ui/**",
            "/v3/api-docs/**",

    };




    public static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final String ENDPOINT_RECOVERY = "http://localhost:9091/api/auth/pw/recovery/";
    public static final String ENDPOINT_LOGIN = "http://localhost:9091/api/auth/login";





    public static final List<String> PRODUCT_FIELDS = Arrays.asList(
            "description", "price",
            "bar_code", "rfid_code", "inventory_number", "serial_number",
            "liable_id", "liable_name", "receiver",
            "kst_id", "asset_status_id", "unit_id", "branch_id", "location_id", "mpk_id", "type_id", "subtype_id",
            "kst", "asset_status", "unit", "branch", "location", "mpk", "type", "subtype",
            "producer", "supplier",
            "scrapping", "scrapping_date", "scrapping_reason", "document",
            "document_date", "warranty_period", "inspection_date",
            "longitude", "latitude"
    );

    public static final List<String> PRODUCT_FIELDS_V1 = Arrays.asList("bar_code", "rfid_code");



    public static final List<String> EVENT_FIELDS = Arrays.asList(
            "info", "inventory_id",
            "user_id", "user_email", "user_name",
            "unknown_products", "scanned_products", "not_scanned_products",
            "unknown_product_amount", "total_product_amount", "scanned_product_amount"
    );
    public static final List<String> EVENT_FIELDS_AMOUNT = Arrays.asList(
            "unknown_product_amount", "total_product_amount", "scanned_product_amount"
    );
}
