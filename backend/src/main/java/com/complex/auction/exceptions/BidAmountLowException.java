package com.complex.auction.exceptions;

public class BidAmountLowException extends RuntimeException {
    public BidAmountLowException(String message) {
        super(message);
    }
}
