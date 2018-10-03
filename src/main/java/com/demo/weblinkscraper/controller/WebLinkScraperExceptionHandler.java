package com.demo.weblinkscraper.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.weblinkscraper.exception.UnableToConnectException;

/**
 * Contains methods to handle errors thrown by Controller classes.
 * @author Nick Walter
 */
@ControllerAdvice
public class WebLinkScraperExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleValidationError(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = UnableToConnectException.class)
    protected ResponseEntity<Object> handleConnectionError(UnableToConnectException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleUnknownError(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "An unexpected error has occurred",
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
