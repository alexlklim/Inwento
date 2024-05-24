package com.alex.asset.exceptions.email;

public class EmailIsNotConfigured extends RuntimeException{
    public EmailIsNotConfigured( final String message) {
        super(message);
    }

}
