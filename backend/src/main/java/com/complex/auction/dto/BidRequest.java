package com.complex.auction.dto;

public record BidRequest(
        Long itemId,
        Long bidderId,
        Double bidderAmount
) {
}
