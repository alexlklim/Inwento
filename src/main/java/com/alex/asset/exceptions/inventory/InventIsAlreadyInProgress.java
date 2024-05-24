package com.alex.asset.exceptions.inventory;

public class InventIsAlreadyInProgress extends RuntimeException{
    public InventIsAlreadyInProgress( final String message) {
        super(message);
    }
}
