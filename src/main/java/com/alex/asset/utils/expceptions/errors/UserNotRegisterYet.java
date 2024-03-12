package com.alex.asset.utils.expceptions.errors;

public class UserNotRegisterYet extends RuntimeException{

    public UserNotRegisterYet(final String message) {
        super(message);
    }
}
