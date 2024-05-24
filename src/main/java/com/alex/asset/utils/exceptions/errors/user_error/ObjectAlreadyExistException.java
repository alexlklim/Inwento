package com.alex.asset.utils.exceptions.errors.user_error;

public class ObjectAlreadyExistException extends RuntimeException{


    public ObjectAlreadyExistException( final String message) {
        super(message);
    }
}
