package com.complex.auction.item;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.complex.auction.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    private String description;

    private LocalDateTime auctionEndTime;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    private String category;

    private String condition;

    @NotNull(message = "Must have a starting bid")
    private Double startingBid;

    private int clicks = 0;

    private int views = 0;

    @Transient
    @JsonIgnore
    private AtomicInteger inMemViews = new AtomicInteger(0);

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @NotNull
    @JsonIgnore
    private User seller;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Bid> bids;

    public Item() {
    }

    public Item(String name, String description, LocalDateTime auctionEndTime,
            byte[] image, String category, String condition,
            Double startingBid, User seller) {
        this.name = name;
        this.description = description;
        this.auctionEndTime = auctionEndTime;
        this.image = image;
        this.category = category;
        this.condition = condition;
        this.startingBid = startingBid;
        this.seller = seller;
    }

    public synchronized void incrementView() {
        views++;
        inMemViews.incrementAndGet();
    }

    public void flushInMemViews() {
        this.views += inMemViews.getAndSet(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(LocalDateTime auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(Double startingBid) {
        this.startingBid = startingBid;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getViews() {
        return views;
    }

    public int getInMemViews() {
        return inMemViews.get();
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setItem(this);
    }

    public void removeBid(Bid bid) {
        bids.remove(bid);
        bid.setItem(null);
    }
}