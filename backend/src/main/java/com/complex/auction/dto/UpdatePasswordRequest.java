package com.complex.auction.dto;

public record UpdatePasswordRequest(
        String username,
        String newPassword
) {
}