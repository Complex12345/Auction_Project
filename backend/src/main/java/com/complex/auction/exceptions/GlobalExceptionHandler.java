package com.complex.auction.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyFoundException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyFoundException(EmailAlreadyFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError apiError = getApiError(ex, request, status);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameAlreadyFoundException.class)
    public ResponseEntity<ApiError> handleUsernameAlreadyFoundException(UsernameAlreadyFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError apiError = getApiError(ex, request, status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UsernameAlreadyFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError apiError = getApiError(ex, request, status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ApiError> handleItemNotFoundException(UsernameAlreadyFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError apiError = getApiError(ex, request, status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(BidNotFoundException.class)
    public ResponseEntity<ApiError> handleBidNotFoundException(BidNotFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError apiError = getApiError(ex, request, status);
        return new ResponseEntity<>(apiError, status);

    }

    @ExceptionHandler(BidAmountLowException.class)
    public ResponseEntity<ApiError> handleBidAmountLowException(BidNotFoundException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError apiError = getApiError(ex, request, status);
        return new ResponseEntity<>(apiError, status);

    }

    private static ApiError getApiError(RuntimeException ex, HttpServletRequest request, HttpStatus httpStatus) {
        return new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                httpStatus.value(),
                LocalDateTime.now()
        );
    }
}
