package com.alex.asset.utils.exceptions.errors;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
