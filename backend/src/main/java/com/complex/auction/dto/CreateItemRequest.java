package com.complex.auction.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public record CreateItemRequest(
        String name,
        String description,
        LocalDateTime auctionEndTime,
        MultipartFile image,
        String category,
        String condition,
        Double startingBid,
        Long sellerId
) {
}

