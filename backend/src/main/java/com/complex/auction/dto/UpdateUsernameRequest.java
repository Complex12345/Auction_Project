package com.complex.auction.dto;

public record UpdateUsernameRequest(
        String oldUsername,
        String newUsername
) {
}