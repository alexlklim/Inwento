package com.alex.asset.utils.expceptions.errors.user_error;

public class UserIsNotEnabled extends RuntimeException{
    public UserIsNotEnabled(final String message) {
        super(message);
    }
}
