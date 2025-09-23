package com.complex.auction.item;


import com.complex.auction.dto.BidRequest;
import com.complex.auction.dto.CreateItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(name = "/item")
public class ItemController {


    public final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PutMapping
    public ResponseEntity<Item> createItem(CreateItemRequest createItemRequest) throws IOException {
        Item createdItem = itemService.createItem(createItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);

    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        Item foundItem = itemService.getItem(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(foundItem);
    }


    @GetMapping("/v1/trending")
    public ResponseEntity<List<Item>> getTrendingItems() {
        ArrayList<Item> trendingItemList;
        trendingItemList = itemService.getTrendingItems();
        return ResponseEntity.status(HttpStatus.FOUND).body(trendingItemList);
    }

    @PatchMapping("/v1/updateName/{id}")
    public ResponseEntity<Item> updateName(@PathVariable Long id, @RequestBody String name) {
        Item updatedItem = itemService.updateName(id, name);

        return ResponseEntity.status(HttpStatus.OK).body(updatedItem);
    }

    @PatchMapping("/v1/updateDescription/{id}")
    public ResponseEntity<Item> updateDescription(@PathVariable("id") Long itemId, @RequestBody String description) {
        Item updatedItem = itemService.updateDescription(itemId, description);

        return ResponseEntity.status(HttpStatus.OK).body(updatedItem);
    }

    @PatchMapping("/v1/placeBid")
    public ResponseEntity<Item> placeBid(@RequestBody BidRequest bidRequest) {
        Item item = itemService.placeBid(bidRequest);

        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @GetMapping("/v1/maxBid/{id}")
    public ResponseEntity<Bid> getHighestBid(@PathVariable("id") Long itemId) {
        Optional<Bid> foundHighestBid = itemService.getHighestBid(itemId);
        return foundHighestBid.map(bid -> ResponseEntity.status(HttpStatus.FOUND).body(bid))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null)
                );
    }

    @PutMapping("/v1/removeBid/{id}")
    public ResponseEntity<Bid> removeForUserBid(@PathVariable("id") Long bidderId) {
        Optional<Bid> deletedBid = itemService.deleteBid(bidderId);

        if (deletedBid.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return deletedBid.map(bid -> ResponseEntity.status(HttpStatus.OK).body(bid))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null)
                );
    }




}
