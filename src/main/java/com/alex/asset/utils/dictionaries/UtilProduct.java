package com.alex.asset.utils.dictionaries;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UtilProduct {

    public final static String ID = "id";
    public final static String ACTIVE = "active";
    public final static String TITLE = "title";
    public final static String DESCRIPTION = "description";
    public final static String PRICE = "price";
    public final static String BAR_CODE = "bar_code";
    public final static String RFID_CODE = "rfid_code";
    public final static String INVENTORY_NUMBER = "inventory_number";
    public final static String SERIAL_NUMBER = "serial_number";
    public final static String LIABLE_ID = "liable_id";
    public final static String LIABLE_NAME = "liable_name";
    public final static String RECEIVER = "receiver";
    public final static String BRANCH_ID = "branch_id";
    public final static String LOCATION_ID = "location_id";
    public final static String KST_ID = "kst_id";
    public final static String ASSET_STATUS_ID = "asset_status_id";
    public final static String UNIT_ID = "unit_id";
    public final static String MPK_ID = "mpk_id";
    public final static String TYPE_ID = "type_id";
    public final static String SUBTYPE_ID = "subtype_id";

    public final static String KST = "kst";
    public final static String ASSET_STATUS = "asset_status";
    public final static String UNIT = "unit";
    public final static String BRANCH = "branch";
    public final static String LOCATION = "location";
    public final static String MPK = "mpk";
    public final static String TYPE = "type";
    public final static String SUBTYPE = "subtype";
    public final static String PRODUCER = "producer";
    public final static String SUPPLIER = "supplier";
    public final static String SCRAPPING = "scrapping";
    public final static String SCRAPPING_DATE = "scrapping_date";
    public final static String SCRAPPING_REASON = "scrapping_reason";
    public final static String DOCUMENT = "document";
    public final static String DOCUMENT_DATE = "document_date";
    public final static String WARRANTY_PERIOD = "warranty_period";
    public final static String INSPECTION_DATE = "inspection_date";
    public final static String LONGITUDE = "longitude";
    public final static String LATITUDE = "latitude";

    // get comments (list of CommentDTO)
    // or add comment (It should be string which belong to this key in json "comments": "my comment"
    // it allows to add only one comment
    public final static String COMMENTS = "comments";
    public final static String HISTORY = "history";
    public final static String SERVICED_HISTORY = "serviced_history";





    public static List<String> getAll() {
        List<String> values = new ArrayList<>();
        Field[] fields = UtilProduct.class.getDeclaredFields();
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

    public static List<String> getFieldsForReport() {
        return List.of(
                DESCRIPTION, PRICE, BAR_CODE, RFID_CODE, SERIAL_NUMBER,
                LIABLE_NAME, RECEIVER, KST, ASSET_STATUS, UNIT,
                BRANCH, LOCATION, MPK, TYPE, SUBTYPE, PRODUCER,
                SUPPLIER, SCRAPPING, SCRAPPING_DATE, SCRAPPING_REASON,
                DOCUMENT, DOCUMENT_DATE, WARRANTY_PERIOD, INSPECTION_DATE,
                LONGITUDE, LATITUDE
        );
    }


    public static List<String> getFieldsShortView() {
        return List.of(
                ID, TITLE, BAR_CODE, RFID_CODE, SERIAL_NUMBER
        );
    }
}