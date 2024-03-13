package com.alex.asset.utils.expceptions.errors;

public class UserIsNotOwnerOfEvent extends RuntimeException{

    public UserIsNotOwnerOfEvent(final String message) {
        super(message);
    }
}
