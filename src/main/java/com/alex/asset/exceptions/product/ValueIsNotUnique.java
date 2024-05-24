package com.alex.asset.exceptions.product;

public class ValueIsNotUnique  extends RuntimeException {
    public ValueIsNotUnique( final String message) {
        super(message);
    }
}