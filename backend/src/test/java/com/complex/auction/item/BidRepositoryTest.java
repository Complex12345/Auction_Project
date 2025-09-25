package com.complex.auction.item;

import com.complex.auction.user.User;
import com.complex.auction.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BidRepositoryTest {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        initData();
    }

    @AfterEach
    void tearDown() {
        bidRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    public void initData() {
        User bidder1 = createUser("john", "john@email.com");
        User bidder2 = createUser("bob", "bob@email.com");
        User bidder3 = createUser("joe", "joe@email.com");

        Item item = new Item();
        item.setName("tv");
        item.setDescription("really good tv");
        item.setSeller(bidder1);
        item.setCategory("furniture");
        item.setCondition("good");
        item.setStartingBid(500.0);
        itemRepository.save(item);

        createBid(item, bidder1, 1.0);
        createBid(item, bidder2, 500.0);
        createBid(item, bidder3, 1000.0);
    }
    private User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password123");
        return userRepository.save(user);
    }

    private void createBid(Item item, User bidder, double amount) {
        Bid bid = new Bid();
        bid.setItem(item);
        bid.setBidder(bidder);
        bid.setAmount(amount);
        bidRepository.save(bid);
    }

    @Test
    void findByItemAndBidder() {
        Item item = itemRepository.findAll().getFirst();
        List<User> bidders = userRepository.findAll();

        List<Double> expectedAmounts = List.of(1.0, 500.0, 1000.0);
        List<String> expectedNames = List.of("john", "bob", "joe");

        for (int i = 0; i < bidders.size(); i++) {
            User bidder = bidders.get(i);
            Optional<Bid> found = bidRepository.findFirstByItemAndBidder(item, bidder);

            assertTrue(found.isPresent(), "Bid should be found in database for " + bidder.getUsername());
            Bid bid = found.get();

            assertEquals(expectedNames.get(i), bid.getBidder().getUsername());
            assertEquals("tv", bid.getItem().getName());
            assertEquals(expectedAmounts.get(i), bid.getAmount());
        }
    }

    @Test
    void findFirstByItemOrderByAmountDesc() {
        Item item = itemRepository.findAll().getFirst();
        Optional<Bid> found = bidRepository.findFirstByItemOrderByAmountDesc(item);
        assertTrue(found.isPresent());
        Bid bid = found.get();

        assertEquals("joe", bid.getBidder().getUsername());
        assertEquals("tv", bid.getItem().getName());
        assertEquals(1000.0, bid.getAmount());
    }

    @Test
    void removeBidByBidder_Id() {
        User bidder = userRepository.findAll().get(0);
        bidRepository.removeBidByBidder_Id(bidder.getId());

        assertTrue(bidRepository.findAll().isEmpty());
    }
}