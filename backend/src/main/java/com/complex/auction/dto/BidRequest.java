package com.complex.auction.dto;

import java.util.UUID;

public record BidRequest(
        Long itemId,
        UUID bidderId,
        Double bidderAmount
) {
}
