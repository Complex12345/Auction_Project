package com.complex.auction.item;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.complex.auction.user.User;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Optional<Bid> findFirstByItemAndBidder(Item item, User bidder);
    Optional<Bid> findFirstByItemOrderByAmountDesc(Item item);
    Optional<Bid> removeBidByBidder_Id(UUID bidderId);
    void deleteByBidder_Id(UUID bidderId);
    List<Bid> findByBidder_Id(UUID userId);
    List<Bid> findByItem_Seller_Id(UUID userId);
}
