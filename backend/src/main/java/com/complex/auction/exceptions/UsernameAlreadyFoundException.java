package com.complex.auction.exceptions;

public class UsernameAlreadyFoundException extends RuntimeException {
    public UsernameAlreadyFoundException(String message) {
        super(message);
    }
}
