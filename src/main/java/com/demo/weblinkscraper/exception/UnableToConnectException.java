package com.demo.weblinkscraper.exception;

/**
 * Exception Class to model when the service cannot get to a given URL.
 * @author Nick Walter
 */
public class UnableToConnectException extends Exception {

    public UnableToConnectException(String message) {
        super(message);
    }

    public UnableToConnectException() {
        super();
    }
}

