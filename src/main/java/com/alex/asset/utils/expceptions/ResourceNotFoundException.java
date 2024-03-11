package com.alex.asset.utils.expceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(
            final String message
    ) {
        super(message);
    }
}
