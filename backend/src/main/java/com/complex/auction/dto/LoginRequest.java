package com.complex.auction.dto;

public record LoginRequest(
        String email,
        String password
) {
}
