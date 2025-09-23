package com.complex.auction.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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

