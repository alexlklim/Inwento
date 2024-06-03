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

}
