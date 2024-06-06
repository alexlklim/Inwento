package com.alex.asset.exceptions.product;

public class ValueIsNotUniqueException extends RuntimeException {
    public ValueIsNotUniqueException(final String message) {
        super(message);
    }
}