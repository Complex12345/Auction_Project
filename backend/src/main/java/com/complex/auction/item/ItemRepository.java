package com.complex.auction.item;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(
            value = "SELECT * FROM Item ORDER BY ( (1.0 * clicks) / NULLIF(views, 0) ) DESC",
            nativeQuery = true
    )
    List<Item> findTrendingItems(Pageable pageable);

    List<Item> findBySeller_Id(UUID userId);

    // List<Bid> findByBidder_Id(UUID bidderId);
    
    
}