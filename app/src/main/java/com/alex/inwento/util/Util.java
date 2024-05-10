package com.alex.inwento.util;

import java.util.Arrays;
import java.util.List;

public class Util {

    public static final String APP_NAME = "Asset Track Pro";
    public static final List<String> FIELDS_SHORT_PRODUCT =
            Arrays.asList("bar_code", "rfid_code", "branch", "location");
    public static final List<String> FIELDS_PRODUCT =
            Arrays.asList("price", "bar_code", "rfid_code", "liable_name", "receiver", "branch_id", "branch");


    public static final String
            IS_RFID_SCAN = "is_rfid_scan",
            IS_SERVER_CONFIGURED = "is_server_configured",
            IS_FILTER = "is_filter",
            IS_REMEMBER_ME = "remember_me",
            FIRST_NAME = "first_name",
            LAST_NAME = "last_name",
            EMAIL = "email",
            PASSWORD = "password",
            ACCESS_TOKEN = "access_token",
            REFRESH_TOKEN = "refresh_token";

    public static final String
            SERVER_ADDRESS = "server_address",
            CODE_PREFIX = "code_prefix",
            CODE_SUFFIX = "code_suffix",
            CODE_POSTFIX = "code_postfix",
            CODE_LENGTH = "code_length",
            CODE_MAX_LENGTH = "code_max_length",
            CODE_MIN_LENGTH = "code_min_length";


    public static final String DATABASE_NAME = "inwento";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_NAME_BRANCH = "branches";
    public static final String TABLE_NAME_EMPLOYEE = "employees";
    public static final String TABLE_NAME_LOCATION = "locations";

}
