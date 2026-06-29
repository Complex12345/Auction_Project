package com.complex.auction.item;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.complex.auction.dto.BidRequest;
import com.complex.auction.dto.CreateItemRequest;
import com.complex.auction.exceptions.ItemNotFoundException;
import com.complex.auction.exceptions.UserNotFoundException;
import com.complex.auction.user.User;
import com.complex.auction.user.UserRepository;

@Service
public class ItemService {

    public final ItemRepository itemRepository;
    public final UserRepository userRepository;
    public final BidRepository bidRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository, BidRepository bidRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bidRepository = bidRepository;
    }

    public Item createItem(CreateItemRequest createItemRequest, UUID sellerId) throws IOException {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new UserNotFoundException("Could not find user with id: " + sellerId));

        Item newItem = new Item();
        newItem.setName(createItemRequest.name());
        newItem.setDescription(createItemRequest.description());
        newItem.setAuctionEndTime(createItemRequest.auctionEndTime());
        newItem.setImage(createItemRequest.image().getBytes());
        newItem.setCategory(createItemRequest.category());
        newItem.setCondition(createItemRequest.condition());
        newItem.setStartingBid(createItemRequest.startingBid());
        newItem.setSeller(seller);

        return itemRepository.save(newItem);
    }

    public Item getItem(Long id) {
        return findItem(id);
    }

    public Optional<Bid> getHighestBid(Long itemId) {
        Item item = findItem(itemId);
        return bidRepository.findFirstByItemOrderByAmountDesc(item);
    }

    public ArrayList<Item> getTrendingItems() {
        return (ArrayList<Item>) itemRepository.findTrendingItems(PageRequest.of(0, 20));
    }

    public Item updateName(Long id, String newName, UUID userId) {
        Item item = findItem(id);

        if (!item.getSeller().getId().equals(userId)) {
            throw new AccessDeniedException("You do not own this item");
        }

        item.setName(newName);
        return itemRepository.save(item);
    }

    public Item findItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + id));
    }

    public Item updateDescription(Long id, String description, UUID userId) {
        Item item = findItem(id);

        if (!item.getSeller().getId().equals(userId)) {
            throw new AccessDeniedException("You do not own this item");
        }

        item.setDescription(description);
        return itemRepository.save(item);
    }

    public Item placeBid(BidRequest bidRequest, UUID userId) {
        Optional<User> bidderOptional = userRepository.findById(userId);
        if (bidderOptional.isEmpty()) {
            throw new UserNotFoundException("Could not find user with id: " + bidRequest.bidderId());
        }
        User bidder = bidderOptional.get();

        Item item = findItem(bidRequest.itemId());

        Optional<Bid> existingBidOptional = bidRepository.findFirstByItemAndBidder(item, bidder);

        if (bidRequest.bidderAmount() < item.getStartingBid()) {
            throw new IllegalArgumentException("Bid amount must be at least " + item.getStartingBid());
        }

        if (existingBidOptional.isPresent()) {
            Bid existingBid = existingBidOptional.get();
            existingBid.setAmount(bidRequest.bidderAmount());
            existingBid.setTimestamp(LocalDateTime.now());
            bidRepository.save(existingBid);
        } else {
            Bid newBid = new Bid();
            newBid.setItem(item);
            newBid.setBidder(bidder);
            newBid.setAmount(bidRequest.bidderAmount());
            newBid.setTimestamp(LocalDateTime.now());
            bidRepository.save(newBid);
        }

        return item;
    }

    public Optional<Bid> deleteBid(Long bidId, UUID userId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new ItemNotFoundException("Bid not found with id: " + bidId));

        if (!bid.getBidder().getId().equals(userId)) {
            throw new AccessDeniedException("You do not own this bid");
        }

        bidRepository.delete(bid);
        return Optional.of(bid);
    }

    public Optional<List<Bid>> getBidList(UUID userId) {
        return Optional.of(bidRepository.findByItem_Seller_Id(userId));
    }

    public Optional<List<Item>> getMyItems(UUID userId) {

        System.out.println("Searching for seller: " + userId);

        List<Item> items = itemRepository.findBySeller_Id(userId);

        System.out.println("Found " + items.size() + " items");

        return Optional.of(items);
    }

}
