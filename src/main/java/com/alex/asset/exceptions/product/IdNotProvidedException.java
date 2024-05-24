package com.alex.asset.exceptions.product;

public class IdNotProvidedException  extends RuntimeException {
    public IdNotProvidedException( final String message) {
        super(message);
    }
}