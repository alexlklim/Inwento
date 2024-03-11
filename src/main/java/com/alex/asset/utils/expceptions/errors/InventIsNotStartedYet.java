package com.alex.asset.utils.expceptions.errors;

public class InventIsNotStartedYet extends RuntimeException{
    public InventIsNotStartedYet(final String message) {
        super(message);
    }
}
