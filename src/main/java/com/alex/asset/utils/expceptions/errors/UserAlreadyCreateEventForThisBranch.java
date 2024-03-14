package com.alex.asset.utils.expceptions.errors;

public class UserAlreadyCreateEventForThisBranch extends RuntimeException{

    public UserAlreadyCreateEventForThisBranch(final String message) {
        super(message);
    }

}
