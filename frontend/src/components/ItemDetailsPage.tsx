import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getItem } from "../api/ItemApi";
import type { Item } from "../types/Item";
import { PlaceBid } from "./PlaceBid";

export function ItemDetailsPage() {
    const { id } = useParams();

    const [item, setItem] = useState<Item | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadItem = async () => {
            if (!id) return;

            try {
                const response = await getItem(Number(id));
                setItem(response);
            } finally {
                setLoading(false);
            }
        };

        loadItem();
    }, [id]);

    if (loading) {
        return <h2>Loading...</h2>;
    }

    if (!item) {
        return <h2>Item not found.</h2>;
    }

    return (
        <div className="item-page">
            <img
                src={item.image}
                alt={item.name}
                width={400}
            />

            <h1>{item.name}</h1>

            <p>{item.description}</p>

            <h3>Category</h3>
            <p>{item.category}</p>

            <h3>Condition</h3>
            <p>{item.condition}</p>

            <h3>Starting Bid</h3>
            <p>${item.startingBid}</p>

            <h3>Current Bid</h3>
            <p>${item.startingBid}</p>

            <PlaceBid itemId={item.id}/>
        </div>
    );
}