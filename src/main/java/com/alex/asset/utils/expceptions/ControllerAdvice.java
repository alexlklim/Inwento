package com.alex.asset.utils.expceptions;


import com.alex.asset.utils.expceptions.errors.InventIsAlreadyInProgress;
import com.alex.asset.utils.expceptions.errors.InventIsAlreadyNotActive;
import com.alex.asset.utils.expceptions.errors.InventIsNotStartedYet;
import com.alex.asset.utils.expceptions.errors.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalArgument(IllegalArgumentException ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(400, ex.getMessage());
    }


    @ExceptionHandler({InventIsAlreadyInProgress.class, InventIsAlreadyNotActive.class, InventIsNotStartedYet.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleInventConflict(Exception ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(HttpStatus.CONFLICT.value(), ex.getMessage());
    }




    @ExceptionHandler({ExpiredJwtException.class, UnsupportedJwtException.class, MalformedJwtException.class,
            SignatureException.class, NonceExpiredException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleJwtExceptions(Exception ex) {
        log.error(TAG + ex.getMessage());
        return new ExceptionBody(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

}
