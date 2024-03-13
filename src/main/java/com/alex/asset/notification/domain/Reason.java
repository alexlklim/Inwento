package com.alex.asset.notification.domain;

public enum Reason {

    COMPANY_WAS_UPDATED,
    PASSWORD_WAS_CHANGED,
    USER_WAS_CREATED, NEW_USER,
    USER_WAS_DISABLED, YOU_WERE_DISABLED, USER_WAS_ENABLED, YOU_WERE_ENABLED,

    USER_WAS_UPDATED, YOU_WERE_UPDATED,

    INVENTORY_IS_START, INVENTORY_IS_FINISHED, PLANNED_INVENTORY,
    PRODUCT_WAS_DISABLED, PRODUCT_WAS_ENABLED, PRODUCT_WAS_SCRAPPED,

    ;


    public static Reason fromString(String value) {
        for (Reason reason : Reason.values()) {
            if (reason.name().equalsIgnoreCase(value)) {
                return reason;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found in enum Reason");
    }

}
