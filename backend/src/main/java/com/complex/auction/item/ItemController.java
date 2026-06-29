package com.complex.auction.item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.complex.auction.dto.BidRequest;
import com.complex.auction.dto.CreateItemRequest;
import com.complex.auction.security.AuthUtil;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    public final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PutMapping(value = "/v1/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Item> createItem(
            @ModelAttribute CreateItemRequest createItemRequest,
            Authentication authentication) throws IOException {

        UUID sellerId = AuthUtil.extractUUID(authentication);
        Item createdItem = itemService.createItem(createItemRequest, sellerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        Item foundItem = itemService.getItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundItem);
    }

    @GetMapping("/v1/trending")
    public ResponseEntity<List<Item>> getTrendingItems() {
        ArrayList<Item> trendingItemList = itemService.getTrendingItems();
        return ResponseEntity.ok(trendingItemList);
    }

    @PatchMapping("/v1/updateName/{id}")
    public ResponseEntity<Item> updateName(
            @PathVariable Long id,
            @RequestBody String name,
            Authentication authentication) {

        UUID userId = AuthUtil.extractUUID(authentication);
        Item updatedItem = itemService.updateName(id, name, userId);
        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/v1/updateDescription/{id}")
    public ResponseEntity<Item> updateDescription(
            @PathVariable("id") Long itemId,
            @RequestBody String description,
            Authentication authentication) {

        UUID userId = AuthUtil.extractUUID(authentication);
        Item updatedItem = itemService.updateDescription(itemId, description, userId);
        return ResponseEntity.ok(updatedItem);
    }

    @PostMapping("/v1/placeBid")
    public ResponseEntity<Item> placeBid(
            @RequestBody BidRequest request,
            Authentication authentication) {

        UUID userId = AuthUtil.extractUUID(authentication);
        Item item = itemService.placeBid(request, userId);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/v1/maxBid/{id}")
    public ResponseEntity<Bid> getHighestBid(@PathVariable("id") Long itemId) {
        Optional<Bid> foundHighestBid = itemService.getHighestBid(itemId);
        return foundHighestBid
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/v1/removeBid/{id}")
    public ResponseEntity<Bid> removeForUserBid(
            @PathVariable("id") Long bidderId,
            Authentication authentication) {

        UUID userId = AuthUtil.extractUUID(authentication);
        Optional<Bid> deletedBid = itemService.deleteBid(bidderId, userId);

        return deletedBid
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/v1/getBidHistory")
    public ResponseEntity<List<Bid>> getBidHistory(Authentication authentication) {
        UUID userId = AuthUtil.extractUUID(authentication);

        Optional<List<Bid>> bidList = itemService.getBidList(userId);

        return bidList
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/v1/myItems")
    public ResponseEntity<List<Item>> getMyItems(Authentication authentication) {

        UUID userId = AuthUtil.extractUUID(authentication);

        Optional<List<Item>> items = itemService.getMyItems(userId);

        return items
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}