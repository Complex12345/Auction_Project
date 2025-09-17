package com.complex.auction.item;


import com.complex.auction.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Table(name = "item")
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;
    private String description;
    private LocalDateTime auctionEndTime;
    private String imageUrl;
    private String category;
    private String condition;  //condition based on quality, example  : Good, fair, bad condition

    @NotNull(message = "Must have a stating bid")
    private Double startingBid;
    private Double currentBid;

    @Transient
    private AtomicInteger inMemViews = new AtomicInteger(0);

    private int views = 0;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    @NotNull
    private User seller;

    public Item() {
    }

    public Item(LocalDateTime auctionEndTime, String category, String condition, Double currentBid, String description, Long id, String imageUrl, String name, User seller, Double startingBid) {
        this.auctionEndTime = auctionEndTime;
        this.category = category;
        this.condition = condition;
        this.currentBid = currentBid;
        this.description = description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.seller = seller;
        this.startingBid = startingBid;

    }

    public Double getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(Double startingBid) {
        this.startingBid = startingBid;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(LocalDateTime auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getViews() {
        return inMemViews.get();
    }


    public synchronized void incrementView(){
        inMemViews.incrementAndGet();
    }
}
