package com.alex.asset.utils.exceptions.errors;

public class InventIsAlreadyInProgress extends RuntimeException{
    public InventIsAlreadyInProgress( final String message) {
        super(message);
    }
}
