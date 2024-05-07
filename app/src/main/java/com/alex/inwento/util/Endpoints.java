package com.alex.inwento.util;

public class Endpoints {

    public static String SERVER = "http://10.1.2.65:9091/api/";

    // Authentication Endpoints
    public static final String LOGIN = "v1/auth/login";
    public static final String REFRESH_TOKEN = "v1/auth/refresh";

    // Inventory Endpoints
    public static final String GET_CURRENT_INVENTORY = "v1/inventory/current";
    public static final String GET_EVENT_BY_ID = "v1/inventory/events/{event_id}";

    // Product Endpoints
    public static final String GET_SHORT_PRODUCTS = "v1/products/all/emp/true";
    public static final String GET_FULL_PRODUCT_BY_ID = "v1/products/{product_id}";
    public static final String GET_FULL_PRODUCT_BY_CODE = "v1/products/filter/unique/{bar_code}/{rfid_code}/null/null";
    public static final String PUT_PRODUCT_UPDATE = "v1/products";

    // Inventory Events Endpoints
    public static final String PUT_SCANNED_BAR_CODE = "v1/inventory/events/{event_id}/products/barcode/{loc_id}";

    public static final String GET_FIELDS = "v1/company/data";

}
