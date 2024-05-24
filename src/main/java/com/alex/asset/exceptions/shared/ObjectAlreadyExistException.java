package com.alex.asset.exceptions.shared;

public class ObjectAlreadyExistException extends RuntimeException{


    public ObjectAlreadyExistException( final String message) {
        super(message);
    }
}
