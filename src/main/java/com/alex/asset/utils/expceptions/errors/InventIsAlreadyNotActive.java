package com.alex.asset.utils.expceptions.errors;

public class InventIsAlreadyNotActive extends RuntimeException{

    public InventIsAlreadyNotActive(final String message) {
        super(message);
    }
}
