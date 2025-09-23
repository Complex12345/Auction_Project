package com.complex.auction.item;


import com.complex.auction.dto.BidRequest;
import com.complex.auction.dto.CreateItemRequest;
import com.complex.auction.exceptions.ItemNotFoundException;
import com.complex.auction.exceptions.UserNotFoundException;
import com.complex.auction.user.User;
import com.complex.auction.user.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

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


    public Item createItem(CreateItemRequest createItemRequest) throws IOException {
        Item newItem = new Item();
        newItem.setName(createItemRequest.name());
        newItem.setDescription(createItemRequest.description());
        newItem.setAuctionEndTime(createItemRequest.auctionEndTime());
        newItem.setImage(createItemRequest.image().getBytes());
        newItem.setCategory(createItemRequest.category());
        newItem.setCondition(createItemRequest.condition());
        newItem.setStartingBid(createItemRequest.startingBid());


        itemRepository.save(newItem);


        return newItem;
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

    public Item updateName(Long id, String newName) {
        Item item = findItem(id);
        item.setName(newName);
        itemRepository.save(item);
        return item;
    }

    public Item findItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + id));
    }

    public Item updateDescription(Long id, String description) {
        Item item = findItem(id);
        item.setDescription(description);
        itemRepository.save(item);
        return item;

    }

    public Item placeBid(BidRequest bidRequest) {
        Optional<User> bidderOptional = userRepository.findById(bidRequest.bidderId());
        if (bidderOptional.isEmpty()) {
            throw new UserNotFoundException("Could not find user with id: " + bidRequest.bidderId());
        }
        User bidder = bidderOptional.get();

        Item item = findItem(bidRequest.itemId());

        Optional<Bid> existingBidOptional = bidRepository.findByItemAndBidder(item, bidder);

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

    public Optional<Bid> deleteBid(Long id){
        return bidRepository.removeBidByBidder_Id(id);

    }



}
