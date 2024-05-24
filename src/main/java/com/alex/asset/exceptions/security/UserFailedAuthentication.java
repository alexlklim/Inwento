package com.alex.asset.exceptions.security;

public class UserFailedAuthentication extends RuntimeException{

    public UserFailedAuthentication( final String message) {
        super(message);
    }

}
