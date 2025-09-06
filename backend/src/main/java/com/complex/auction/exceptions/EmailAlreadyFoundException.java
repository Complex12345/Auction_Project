package com.complex.auction.exceptions;

public class EmailAlreadyFoundException extends RuntimeException {
    public EmailAlreadyFoundException(String message) {
        super(message);
    }
}
