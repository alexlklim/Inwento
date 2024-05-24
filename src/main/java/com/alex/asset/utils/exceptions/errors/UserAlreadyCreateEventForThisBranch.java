package com.alex.asset.utils.exceptions.errors;

public class UserAlreadyCreateEventForThisBranch extends RuntimeException{

    public UserAlreadyCreateEventForThisBranch( final String message) {
        super(message);
    }

}
