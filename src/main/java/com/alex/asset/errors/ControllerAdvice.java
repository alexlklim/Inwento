package com.alex.asset.errors;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {


//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ExceptionBody handleResourceNotFound(
//            final ResourceNotFoundException e
//    ) {
//        return new ExceptionBody(e.getMessage());
//    }
}
