package com.alex.asset.exceptions;


import com.alex.asset.exceptions.company.KSTNumIsToShortException;
import com.alex.asset.exceptions.company.LabelSizeIsIncorrectException;
import com.alex.asset.exceptions.email.EmailIsNotConfigured;
import com.alex.asset.exceptions.inventory.InventIsAlreadyInProgressException;
import com.alex.asset.exceptions.product.*;
import com.alex.asset.exceptions.security.UserFailedAuthentication;
import com.alex.asset.exceptions.security.UserNotRegisterYet;
import com.alex.asset.exceptions.shared.ObjectAlreadyExistException;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
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
        return new ExceptionBody(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            ValueIsNotAllowedException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalArgument(Exception ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }


    @ExceptionHandler({
            LabelSizeIsIncorrectException.class,
            ValueIsNotUniqueException.class,
            IllegalStateException.class,
            IOException.class,
            ObjectAlreadyExistException.class,
            UserNotRegisterYet.class,
            IdNotProvidedException.class,
            LengthOfCodeNotConfiguredException.class,
            KSTNumIsToShortException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleUserAlreadyExistException(Exception ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(HttpStatus.CONFLICT.value(), ex.getMessage());
    }




    @ExceptionHandler({
            EmailIsNotConfigured.class,
            InventIsAlreadyInProgressException.class,
            ActionIsNotPossibleException.class
    })
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
            NonceExpiredException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleJwtExceptions(Exception ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

}