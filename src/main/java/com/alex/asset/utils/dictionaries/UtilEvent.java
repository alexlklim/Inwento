package com.alex.asset.utils.dictionaries;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UtilEvent {

    public final static String ID = "id";
    public final static String ACTIVE = "active";
    public final static String INFO = "info";
    public final static String INVENTORY_ID = "inventory_id";
    public final static String BRANCH_ID = "branch_id";
    public final static String BRANCH = "branch";
    public final static String USER_ID = "user_id";
    public final static String USER_EMAIL = "user_email";
    public final static String USER_NAME = "user_name";
    public final static String UNKNOWN_PRODUCTS = "unknown_products";
    public final static String SCANNED_PRODUCTS = "scanned_products";
    public final static String NOT_SCANNED_PRODUCTS = "not_scanned_products";
    public final static String UNKNOWN_PRODUCT_AMOUNT = "unknown_product_amount";
    public final static String TOTAL_PRODUCT_AMOUNT = "total_product_amount";
    public final static String SCANNED_PRODUCT_AMOUNT = "scanned_product_amount";


    public static List<String> getAll() {
        List<String> values = new ArrayList<>();
        Field[] fields = UtilEvent.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                try {
                    values.add((String) field.get(null));
                } catch (IllegalAccessException e) {
                    log.error("IllegalAccessException");
                }
            }
        }
        return values;
    }


    public static List<String> getFieldsSimpleView() {
        return List.of(
                INFO, BRANCH, USER_NAME, UNKNOWN_PRODUCT_AMOUNT, TOTAL_PRODUCT_AMOUNT, SCANNED_PRODUCT_AMOUNT
        );
    }
}
