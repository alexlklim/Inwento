package com.alex.asset.exceptions.security;

public class UserNotRegisterYet extends RuntimeException{

    public UserNotRegisterYet( final String message) {
        super(message);
    }
}
