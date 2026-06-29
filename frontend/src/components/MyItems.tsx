import { useEffect, useState } from "react";
import { getMyItems } from "../api/ItemApi";
import { AuctionItem } from "./AuctionItem";
import type { Item } from "../types/Item";

export function MyItems() {

    const [items, setItems] = useState<Item[]>([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState("");

    useEffect(() => {
        loadItems();
    }, []);

    const loadItems = async () => {
        setLoading(true);
        setMessage("");

        try {
            const myItems = await getMyItems();
            console.log(items);
            setItems(myItems);
        } catch (error) {
            console.error(error);
            setMessage("Failed to load your auction items.");
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (message) {
        return <p>{message}</p>;
    }

    if (items.length === 0) {
        return <p>You have no auction items.</p>;
    }

    return (
        <div>
            <h2>My Auction Items</h2>

            <div className="dashboard-content">
                {items.map((item) => (
                    <AuctionItem
                        key={item.id}
                        item={item}
                    />
                ))}
            </div>
        </div>
    );
}