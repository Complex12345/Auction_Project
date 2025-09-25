package com.complex.auction.item;


import com.complex.auction.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Optional<Bid> findFirstByItemAndBidder(Item item, User bidder);
    Optional<Bid> findFirstByItemOrderByAmountDesc(Item item);
    Optional<Bid> removeBidByBidder_Id(Long bidderId);
}
