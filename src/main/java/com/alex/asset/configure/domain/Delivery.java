package com.alex.asset.configure.domain;

import lombok.Getter;

@Getter
public enum Delivery {

    COURIER("kurier"),
    PERSONAL_PICKUP("odbiór osobisty");

    private final String displayName;

    Delivery(String displayName) {
        this.displayName = displayName;
    }


    public static Delivery fromString(String value) {
        for (Delivery delivery : Delivery.values()) {
            if (delivery.name().equalsIgnoreCase(value)) {
                return delivery;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found in enum Delivery");
    }
}
