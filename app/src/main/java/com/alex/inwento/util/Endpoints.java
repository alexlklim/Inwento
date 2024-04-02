package com.alex.inwento.util;

public class Endpoints {
    private static final String SERVER_CSMM = "http://10.30.0.66:9091/api";
    private static final String SERVER = "http://10.1.2.66:9091/api";

    public static final String LOGIN = SERVER + "/v1/auth/login";
    public static final String REFRESH_TOKEN = SERVER + "/v1/auth/refresh";
    public static final String GET_IS_INVENTORY_ACTIVE = SERVER + "/v1/invent/active";
    public static final String GET_CURRENT_INVENTORY = SERVER + "/v1/invent/current";
    public static final String GET_ALL_MY_EVENTS = SERVER + "/v1/event/invent/"; // + inventory id
    public static final String GET_EVENT_BY_ID = SERVER + "/v1/event/"; // + event id
    public static final String GET_SHORT_PRODUCT = SERVER + "/v1/product/app/";
    public static final String GET_SHORT_PRODUCT_BY_BAR_CODE = SERVER + "/v1/product/filter/bar-code/";
    public static final String GET_FIELDS = SERVER + "/v1/company/all";
    public static final String ADD_PRODUCTS = SERVER + "/v1/event/";  // event id

    public static final String ADD_EVENT = SERVER + "/v1/event";



}
