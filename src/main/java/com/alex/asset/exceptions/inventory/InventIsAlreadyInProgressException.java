package com.alex.asset.exceptions.inventory;

public class InventIsAlreadyInProgressException extends RuntimeException{
    public InventIsAlreadyInProgressException(final String message) {
        super(message);
    }
}
