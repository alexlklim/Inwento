package com.alex.asset.utils.dictionaries;

import java.util.List;

public class UtilsInventory {


    public final static String INFO = "info"; // all fields belong to "info"

    public final static String ID = "id";
    public final static String IS_ACTIVE = "is_active";
    public final static String START_DATE = "start_date";
    public final static String FINISH_DATE = "finish_date";
    public final static String IS_FINISHED = "is_finished";




    public final static String PRODUCTS_AMOUNT = "products_amount"; // all these fields belong to "products_amount"
    public final static String UNKNOWN_PRODUCT_AMOUNT = "unknown_product_amount";
    public final static String TOTAL_PRODUCT_AMOUNT = "total_product_amount";
    public final static String SCANNED_PRODUCT_AMOUNT = "scanned_product_amount";
    public final static String NUMBERS_OF_EVENTS = "number_of_events";



    // it belongs to "events", user need to send brief or full view, server return response like "events"
    public final static String EVENTS = "events";




    public static List<String> getAllFields() {
        return List.of(INFO, PRODUCTS_AMOUNT, EVENTS);
    }

}
