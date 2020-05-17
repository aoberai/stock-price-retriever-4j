package com.crazzyghost.alphavantage;

/**
 * Exception class for the library
 *
 * @author crazzyghost
 * @since 1.0.0
 */
public class AlphaVantageException extends RuntimeException {

    public AlphaVantageException() {
        super();
    }

    public AlphaVantageException(String msg) {
        super(msg);
    }

}
