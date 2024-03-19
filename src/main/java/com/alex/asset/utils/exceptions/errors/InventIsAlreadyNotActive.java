package com.alex.asset.utils.exceptions.errors;

public class InventIsAlreadyNotActive extends RuntimeException{

    public InventIsAlreadyNotActive(final String message) {
        super(message);
    }
}
