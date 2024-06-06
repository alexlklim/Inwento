package com.alex.asset.exceptions.product;

public class ValueIsNotAllowedException extends RuntimeException {
    public ValueIsNotAllowedException(final String message) {
        super(message);
    }
}