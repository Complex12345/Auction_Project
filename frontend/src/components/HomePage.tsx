import { useEffect, useState } from "react";
import { AuctionItem } from "./AuctionItem.tsx";
import { getTrendingItems } from "../api/ItemApi.ts";
import type { Item } from "../types/Item.ts";

export function HomePage() {
    const [items, setItems] = useState<Item[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchTrendingItems = async () => {
            try {
                const data = await getTrendingItems();
                setItems(data);
            } catch (err) {
                setError("Failed to load trending auctions.");
            } finally {
                setLoading(false);
            }
        };

        fetchTrendingItems();
    }, []);

    if (loading) {
        return <h2>Loading...</h2>;
    }

    if (error) {
        return <h2>{error}</h2>;
    }

    return (
        <div>
            <h2>Trending Auctions</h2>

            {items.map((item) => (
                <AuctionItem
                    key={item.id}
                    item={item}
                />
            ))}
        </div>
    );
}