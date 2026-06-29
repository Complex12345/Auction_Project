package com.complex.auction.item;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import com.complex.auction.dto.BidRequest;
import com.complex.auction.exceptions.ItemNotFoundException;
import com.complex.auction.exceptions.UserNotFoundException;
import com.complex.auction.user.User;
import com.complex.auction.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void getItem_ShouldReturnItem() {
        UUID sellerId = UUID.randomUUID();

        User seller = new User();
        seller.setId(sellerId);

        Item item = new Item();
        item.setId(1L);
        item.setSeller(seller);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemService.getItem(1L);

        assertEquals(item, result);
    }

    @Test
    void getItem_ShouldThrow_WhenItemDoesNotExist() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class,
                () -> itemService.getItem(1L));
    }

    @Test
    void updateName_ShouldUpdateName() {
        UUID sellerId = UUID.randomUUID();

        User seller = new User();
        seller.setId(sellerId);

        Item item = new Item();
        item.setSeller(seller);
        item.setName("Old");

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        Item updated = itemService.updateName(1L, "New Name", sellerId);

        assertEquals("New Name", updated.getName());
    }

    @Test
    void updateName_ShouldThrowAccessDenied() {
        UUID ownerId = UUID.randomUUID();
        UUID otherUser = UUID.randomUUID();

        User seller = new User();
        seller.setId(ownerId);

        Item item = new Item();
        item.setSeller(seller);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(AccessDeniedException.class,
                () -> itemService.updateName(1L, "TV", otherUser));
    }

    @Test
    void getHighestBid_ShouldReturnHighestBid() {
        UUID sellerId = UUID.randomUUID();

        User seller = new User();
        seller.setId(sellerId);

        Item item = new Item();
        item.setSeller(seller);

        Bid bid = new Bid();
        bid.setAmount(Double.valueOf(100));

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(bidRepository.findFirstByItemOrderByAmountDesc(item))
                .thenReturn(Optional.of(bid));

        Optional<Bid> result = itemService.getHighestBid(1L);

        assertTrue(result.isPresent());
        assertEquals(100, result.get().getAmount());
    }

    @Test
    void deleteBid_ShouldDeleteBid() {
        UUID bidderId = UUID.randomUUID();

        User bidder = new User();
        bidder.setId(bidderId);

        Bid bid = new Bid();
        bid.setBidder(bidder);

        when(bidRepository.findById(1L)).thenReturn(Optional.of(bid));

        itemService.deleteBid(1L, bidderId);

        verify(bidRepository).delete(bid);
    }

    @Test
    void deleteBid_ShouldThrowAccessDenied() {
        UUID owner = UUID.randomUUID();
        UUID other = UUID.randomUUID();

        User bidder = new User();
        bidder.setId(owner);

        Bid bid = new Bid();
        bid.setBidder(bidder);

        when(bidRepository.findById(1L)).thenReturn(Optional.of(bid));

        assertThrows(AccessDeniedException.class,
                () -> itemService.deleteBid(1L, other));
    }

    @Test
    void placeBid_ShouldThrow_WhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        BidRequest request = new BidRequest(1L, userId, 100.0);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() -> itemService.placeBid(request, userId));
    }
}