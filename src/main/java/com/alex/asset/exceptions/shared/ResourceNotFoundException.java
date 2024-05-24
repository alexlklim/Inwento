package com.alex.asset.exceptions.shared;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException( final String message) {
        super(message);
    }
}
