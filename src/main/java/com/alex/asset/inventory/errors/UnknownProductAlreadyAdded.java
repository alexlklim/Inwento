package com.alex.asset.inventory.errors;

public class UnknownProductAlreadyAdded  extends RuntimeException{


    public UnknownProductAlreadyAdded(final String message) {
        super(message);
    }
}
