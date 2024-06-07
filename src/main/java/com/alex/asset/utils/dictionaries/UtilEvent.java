package com.alex.asset.utils.dictionaries;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UtilEvent extends UtilsBase{


    public final static String INFO = "info";


    public final static String INVENTORY_ID = "inventory_id";
    public final static String BRANCH_ID = "branch_id";
    public final static String BRANCH = "branch";


    public final static String USER = "user";
    public final static String USER_ID = "user_id";
    public final static String USER_EMAIL = "user_email";
    public final static String USER_NAME = "user_name";


    public final static String UNKNOWN_PRODUCTS = "unknown_products";
    public final static String SCANNED_PRODUCTS = "scanned_products";
    public final static String NOT_SCANNED_PRODUCTS = "not_scanned_products";


    public final static String PRODUCT_AMOUNT = "product_amount";
    public final static String UNKNOWN_PRODUCT_AMOUNT = "unknown_product_amount";
    public final static String TOTAL_PRODUCT_AMOUNT = "total_product_amount";
    public final static String SCANNED_PRODUCT_AMOUNT = "scanned_product_amount";


    public static List<String> getAll() {
        return List.of(
                INFO, USER, PRODUCT_AMOUNT,
                UNKNOWN_PRODUCTS, SCANNED_PRODUCTS, NOT_SCANNED_PRODUCTS
        );
    }


    public static List<String> getFieldsSimpleView() {
        return List.of(
                INFO, BRANCH, USER_NAME, UNKNOWN_PRODUCT_AMOUNT, TOTAL_PRODUCT_AMOUNT, SCANNED_PRODUCT_AMOUNT
        );
    }
}
