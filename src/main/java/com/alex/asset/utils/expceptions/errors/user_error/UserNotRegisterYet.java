package com.alex.asset.utils.expceptions.errors.user_error;

public class UserNotRegisterYet extends RuntimeException{

    public UserNotRegisterYet(final String message) {
        super(message);
    }
}