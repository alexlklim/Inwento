package com.alex.asset.utils.expceptions.errors;

public class EmailIsNotConfigured extends RuntimeException{
    public EmailIsNotConfigured(final String message) {
        super(message);
    }

}
