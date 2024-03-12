package com.alex.asset.utils.expceptions.errors;

public class UserAlreadyExistException extends RuntimeException{


    public UserAlreadyExistException(final String message) {
        super(message);
    }
}
