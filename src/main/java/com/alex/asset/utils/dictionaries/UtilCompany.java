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
    public final static String LABEL_WIDTH = "label_width";
    public final static String LABEL_HEIGHT = "label_height";
    public final static String LABEL_TYPE = "label_type";
    public final static String EMAIL_HOST = "email_host";
    public final static String EMAIL_PORT = "email_port";
    public final static String EMAIL_USERNAME = "email_username";
    public final static String EMAIL_PASSWORD = "email_password";
    public final static String EMAIL_PROTOCOL = "email_protocol";
    public final static String EMAIL_CONFIGURED = "email_configured";
    public final static String RFID_LENGTH = "rfid_length";
    public final static String RFID_LENGTH_MAX = "rfid_length_max";
    public final static String RFID_LENGTH_MIN = "rfid_length_min";
    public final static String RFID_PREFIX = "rfid_prefix";
    public final static String RFID_SUFFIX = "rfid_suffix";
    public final static String RFID_POSTFIX = "rfid_postfix";
    public final static String BAR_CODE_LENGTH = "bar_code_length";
    public final static String BAR_CODE_LENGTH_MAX = "bar_code_length_max";
    public final static String BAR_CODE_LENGTH_MIN = "bar_code_length_min";
    public final static String BAR_CODE_PREFIX = "bar_code_prefix";
    public final static String BAR_CODE_SUFFIX = "bar_code_suffix";
    public final static String BAR_CODE_POSTFIX = "bar_code_postfix";


    public static List<String> getAll() {
        List<String> values = new ArrayList<>();
        Field[] fields = UtilCompany.class.getDeclaredFields();
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
                COMPANY, CITY, STREET, ZIP_CODE, NIP, REGON, PHONE, EMAIL,
                LABEL_WIDTH, LABEL_HEIGHT, LABEL_TYPE, EMAIL_HOST, EMAIL_PORT,
                EMAIL_USERNAME, EMAIL_PASSWORD, EMAIL_PROTOCOL, EMAIL_CONFIGURED,
                RFID_LENGTH, RFID_LENGTH_MAX, RFID_LENGTH_MIN, RFID_PREFIX,
                RFID_SUFFIX, RFID_POSTFIX, BAR_CODE_LENGTH, BAR_CODE_LENGTH_MAX,
                BAR_CODE_LENGTH_MIN, BAR_CODE_PREFIX, BAR_CODE_SUFFIX, BAR_CODE_POSTFIX
        );
    }
}