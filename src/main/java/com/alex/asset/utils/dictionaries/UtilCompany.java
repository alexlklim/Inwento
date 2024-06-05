package com.alex.asset.utils.dictionaries;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UtilCompany {
    public final static String COMPANY = "company";
    public final static String CITY = "city";
    public final static String STREET = "street";
    public final static String ZIP_CODE = "zip_code";
    public final static String NIP = "nip";
    public final static String REGON = "regon";
    public final static String PHONE = "phone";
    public final static String EMAIL = "email";


    public final static String LABEL_CONFIG = "label_config";
    public final static String LABEL_WIDTH = "label_width";
    public final static String LABEL_HEIGHT = "label_height";
    public final static String LABEL_TYPE = "label_type";


    public final static String EMAIL_CONFIG = "email_config";
    public final static String EMAIL_HOST = "email_host";
    public final static String EMAIL_PORT = "email_port";
    public final static String EMAIL_USERNAME = "email_username";
    public final static String EMAIL_PASSWORD = "email_password";
    public final static String EMAIL_PROTOCOL = "email_protocol";
    public final static String EMAIL_CONFIGURED = "email_configured";


    public final static String RFID_CODE = "rfid_code";
    public final static String RFID_LENGTH = "rfid_length";
    public final static String RFID_LENGTH_MAX = "rfid_length_max";
    public final static String RFID_LENGTH_MIN = "rfid_length_min";
    public final static String RFID_PREFIX = "rfid_prefix";
    public final static String RFID_SUFFIX = "rfid_suffix";
    public final static String RFID_POSTFIX = "rfid_postfix";

    public final static String BARCODE = "barcode";
    public final static String BARCODE_LENGTH = "barcode_length";
    public final static String BARCODE_LENGTH_MAX = "barcode_length_max";
    public final static String BARCODE_LENGTH_MIN = "barcode_length_min";
    public final static String BARCODE_PREFIX = "barcode_prefix";
    public final static String BARCODE_SUFFIX = "barcode_suffix";
    public final static String BARCODE_POSTFIX = "barcode_postfix";


    public static List<String> getAll() {
        return List.of(
                COMPANY, LABEL_CONFIG, EMAIL_CONFIG, RFID_CODE, BARCODE
        );
    }

    public static List<String> getFieldsSimpleView() {
        return List.of(
                COMPANY
        );
    }
}