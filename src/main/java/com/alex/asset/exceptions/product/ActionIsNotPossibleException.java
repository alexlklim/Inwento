package com.alex.asset.exceptions.product;

public class ActionIsNotPossibleException extends RuntimeException {
    public ActionIsNotPossibleException(final String message) {
        super(message);
    }
}