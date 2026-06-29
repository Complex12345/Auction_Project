package com.complex.auction.dto;

import java.time.LocalDateTime;

public record BidHistoryResponse(
    Long id,
    Long itemId,
    Double amount,
    LocalDateTime timestamp
) {}