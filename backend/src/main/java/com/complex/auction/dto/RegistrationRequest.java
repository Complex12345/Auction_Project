package com.complex.auction.dto;

public record RegistrationRequest(
        String email,
        String username,
        String password
) {
}
