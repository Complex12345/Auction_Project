package com.complex.auction.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;

import com.complex.auction.user.CustomUserDetails;

public class AuthUtil {
    public static UUID extractUUID(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUuid();
    }
}