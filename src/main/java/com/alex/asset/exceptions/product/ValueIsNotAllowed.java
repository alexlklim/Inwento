package com.alex.asset.exceptions.product;

public class ValueIsNotAllowed   extends RuntimeException {
    public ValueIsNotAllowed( final String message) {
        super(message);
    }
}