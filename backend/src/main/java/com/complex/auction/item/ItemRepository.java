package com.complex.auction.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(
            value = "SELECT * FROM Item ORDER BY ( (1.0 * clicks) / NULLIF(views, 0) ) DESC",
            nativeQuery = true
    )
    List<Item> findTrendingItems(Pageable pageable);






}
