package com.alex.asset.inventory.domain.event;

public enum CodeType {

    RFID_CODE, BAR_CODE;


    public static CodeType fromString(String codeTypeString) {
        for (CodeType codeType : CodeType.values()) {
            if (codeType.toString().equalsIgnoreCase(codeTypeString)) {
                return codeType;
            }
        }
        throw new IllegalArgumentException("Invalid code type: " + codeTypeString);
    }
}
