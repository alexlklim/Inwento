package com.alex.asset.utils.expceptions.errors.user_error;

public class UserAlreadyExistException extends RuntimeException{


    public UserAlreadyExistException(final String message) {
        super(message);
    }
}
