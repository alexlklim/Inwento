package com.alex.asset.utils.expceptions.errors;

public class InventIsAlreadyInProgress extends RuntimeException{
    public InventIsAlreadyInProgress(final String message) {
        super(message);
    }
}
