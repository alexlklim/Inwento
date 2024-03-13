package com.alex.asset.utils.expceptions.errors.user_error;

public class UserIsNotOwnerOfEvent extends RuntimeException{

    public UserIsNotOwnerOfEvent(final String message) {
        super(message);
    }
}
