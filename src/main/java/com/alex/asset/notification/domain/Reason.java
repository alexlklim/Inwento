package com.alex.asset.notification.domain;

public enum Reason {

    INVENT, CONTROL, NEED_TO_APPROVE, HIDE_OPERATION;


    public static Reason fromString(String value) {
        for (Reason reason : Reason.values()) {
            if (reason.name().equalsIgnoreCase(value)) {
                return reason;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found in enum Reason");
    }

}
