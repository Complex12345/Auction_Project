import type {Item} from "../types/Item.ts";

export let AuctionList: Item[] = [
    {
        id: 1,
        name: "Vintage Clock",
        description: "An antique wall clock from the 19th century.",
        category: "Antiques",
        imageURL: "https://example.com/images/clock.jpg",
        condition: "Good",
        auctionEndTime: "2025-09-20T18:00:00",
        currentBid: 75
    },
    {
        id: 2,
        name: "Gaming Laptop",
        description: "High-performance gaming laptop with RTX 4080.",
        category: "Electronics",
        imageURL: "https://example.com/images/laptop.jpg",
        condition: "Excellent",
        auctionEndTime: "2025-09-22T12:00:00",
        currentBid: 1200
    },
    {
        id: 3,
        name: "Leather Sofa",
        description: "Brown leather 3-seater sofa in fair condition.",
        category: "Furniture",
        imageURL: "https://example.com/images/sofa.jpg",
        condition: "Fair",
        auctionEndTime: "2025-09-25T21:30:00",
        currentBid: 300
    }
];
