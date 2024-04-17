package com.alex.asset.utils.exceptions.errors;

public class AccessNotAllowed  extends RuntimeException {
    public AccessNotAllowed(final String message) {
        super(message);
    }
}