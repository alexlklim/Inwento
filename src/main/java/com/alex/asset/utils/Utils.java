package com.alex.asset.utils;

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
            "bar_code", "rfid_code", " ", "serial_number",
            "liable_id", "liable_name", "receiver",
            "branch_id", "location_id",
            "kst_id", "asset_status_id", "unit_id", "mpk_id", "type_id", "subtype_id",
                "kst", "asset_status", "unit", "branch", "location", "mpk", "type", "subtype",
            "producer", "supplier",
            "scrapping", "scrapping_date", "scrapping_reason", "document",
            "document_date", "warranty_period", "inspection_date",
            "longitude", "latitude"
    );

    public static final List<String> EVENT_FIELDS = Arrays.asList(
            "info", "inventory_id", "branch_id", "branch",
            "user_id", "user_email", "user_name",
            "unknown_products", "scanned_products", "not_scanned_products",
            "unknown_product_amount", "total_product_amount", "scanned_product_amount"
    );
    public static final List<String> EVENT_SHORT_FIELDS = Arrays.asList(
            "info", "branch", "user_name",
            "unknown_product_amount", "total_product_amount", "scanned_product_amount"
    );

    public static final List<String> COMPANY_FIELDS = Arrays.asList(
            "company", "city", "street", "zip_code", "nip", "regon", "phone", "email",
            "label_width", "label_height", "label_type",
            "email_host", "email_port", "email_username", "email_password", "email_protocol", "email_configured",
            "all_configuration"
    );
    public static final List<String> COMPANY_FIELDS_SIMPLE = Arrays.asList(
            "company", "city", "street", "zip_code", "nip", "regon", "phone", "email",
            "label_width", "label_height", "label_type",
            "email_host", "email_port", "email_username", "email_password", "email_protocol", "email_configured"
    );
}
