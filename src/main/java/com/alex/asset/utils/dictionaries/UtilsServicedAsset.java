package com.alex.asset.utils.dictionaries;

import java.util.List;

public class UtilsServicedAsset {


    public final static String ID = "id";

    public final static String ACTIVE = "active";
    public final static String SERVICE_START_DATE = "service_start_date";
    public final static String SERVICE_END_DATE = "service_end_date";
    public final static String PLANNED_SERVICE_PERIOD = "planned_service_period";
    public final static String DELIVERY = "delivery";
    public final static String PRODUCT = "product";
    public final static String PRODUCT_ID = "product_id";

    public final static String SERVICE_PROVIDER_ID = "service_provider_id";
    public final static String SERVICE_PROVIDER_COMPANY = "service_provider_company";
    public final static String SERVICE_PROVIDER_NIP = "service_provider_nip";
    public final static String SERVICE_PROVIDER_ADDRESS = "service_provider_address";

    public final static String CONTACT_PERSON_ID = "contact_person_id";
    public final static String CONTACT_PERSON_NAME = "contact_person_name";
    public final static String CONTACT_PERSON_EMAIL = "contact_person_email";
    public final static String CONTACT_PERSON_PHONE_NUMBER = "contact_person_phone_number";

    public final static String SEND_BY_ID = "send_by_id";
    public final static String SEND_BY_NAME = "send_by_name";
    public final static String SEND_BY_EMAIL = "send_by_email";


    public final static String RECEIVER_BY_ID = "received_by_id";
    public final static String RECEIVER_BY_NAME = "received_by_name";
    public final static String RECEIVER_BY_EMAIL = "received_by_email";


    public static List<String> getFieldsShortView() {
        return List.of(
                ID, SERVICE_START_DATE, SERVICE_END_DATE, PLANNED_SERVICE_PERIOD,
                SERVICE_PROVIDER_ID, SERVICE_PROVIDER_COMPANY,
                CONTACT_PERSON_ID, CONTACT_PERSON_NAME, CONTACT_PERSON_EMAIL,
                SEND_BY_NAME, RECEIVER_BY_NAME
        );
    }

    public static List<String> getAllFields() {
        return List.of(
                ID, ACTIVE, SERVICE_START_DATE, SERVICE_END_DATE, PLANNED_SERVICE_PERIOD, DELIVERY,
                PRODUCT, PRODUCT_ID, SERVICE_PROVIDER_ID, SERVICE_PROVIDER_COMPANY,
                SERVICE_PROVIDER_NIP, SERVICE_PROVIDER_ADDRESS, CONTACT_PERSON_ID,
                CONTACT_PERSON_NAME, CONTACT_PERSON_EMAIL, CONTACT_PERSON_PHONE_NUMBER,
                SEND_BY_ID, SEND_BY_NAME, SEND_BY_EMAIL, RECEIVER_BY_ID,
                RECEIVER_BY_NAME, RECEIVER_BY_EMAIL
        );
    }
}
