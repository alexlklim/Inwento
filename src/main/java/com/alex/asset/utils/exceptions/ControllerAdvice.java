package com.alex.asset.utils.exceptions;


import com.alex.asset.utils.exceptions.errors.*;
import com.alex.asset.utils.exceptions.errors.user_error.ObjectAlreadyExistException;
import com.alex.asset.utils.exceptions.errors.user_error.UserFailedAuthentication;
import com.alex.asset.utils.exceptions.errors.user_error.UserIsNotOwnerOfEvent;
import com.alex.asset.utils.exceptions.errors.user_error.UserNotRegisterYet;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    private final String TAG = "CONTROLLER_ADVICE - ";


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(404, ex.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            UserIsNotOwnerOfEvent.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalArgument(IllegalArgumentException ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(400, ex.getMessage());
    }


    @ExceptionHandler({
            IllegalStateException.class,
            IOException.class,
            ObjectAlreadyExistException.class,
            UserAlreadyCreateEventForThisBranch.class,
            UserNotRegisterYet.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleUserAlreadyExistException(ObjectAlreadyExistException ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(HttpStatus.CONFLICT.value(), ex.getMessage());
    }




    @ExceptionHandler({
            EmailIsNotConfigured.class,
            InventIsAlreadyInProgress.class,
            InventIsAlreadyNotActive.class,
            InventIsNotStartedYet.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleInventConflict(Exception ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(HttpStatus.CONFLICT.value(), ex.getMessage());
    }




    @ExceptionHandler({
            UserFailedAuthentication.class,
            ExpiredJwtException.class,
            UnsupportedJwtException.class,
            MalformedJwtException.class,
            SignatureException.class,
            MailAuthenticationException.class,
            NonceExpiredException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleJwtExceptions(Exception ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

}
