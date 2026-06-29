import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { Item } from "../types/Item";
import type { Bid } from "../types/Bid";
import { getHighestBid, getItem } from "../api/ItemApi";
import { PlaceBid } from "./PlaceBid";
import "../css/ItemDetailsPage.css";

export function ItemDetailsPage() {
    const { id } = useParams();

    const [item, setItem] = useState<Item | null>(null);
    const [highestBid, setHighestBid] = useState<Bid | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadItem = async () => {
            if (!id) {
                return;
            }

            try {
                const itemId = Number(id);

                const [itemResponse, highestBidResponse] = await Promise.all([
                    getItem(itemId),
                    getHighestBid(itemId)
                ]);

                setItem(itemResponse);
                setHighestBid(highestBidResponse);
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        };

        loadItem();
    }, [id]);

    if (loading) {
        return (
            <div className="item-page">
                <h2>Loading...</h2>
            </div>
        );
    }

    if (!item) {
        return (
            <div className="item-page">
                <h2>Item not found.</h2>
            </div>
        );
    }

    return (
        <div className="item-page">
            <div className="item-image-container">
                <img
                    className="item-image"
                    src={`data:image/jpeg;base64,${item.image}`}
                    alt={item.name}
                />
            </div>

            <div className="item-info">
                <h1 className="item-title">{item.name}</h1>

                <p className="item-description">
                    {item.description}
                </p>

                <div className="item-details">
                    <div className="detail-card">
                        <span className="detail-label">Category</span>
                        <span className="detail-value">
                            {item.category}
                        </span>
                    </div>

                    <div className="detail-card">
                        <span className="detail-label">Condition</span>
                        <span className="detail-value">
                            {item.condition}
                        </span>
                    </div>

                    <div className="detail-card">
                        <span className="detail-label">Starting Bid</span>
                        <span className="detail-value">
                            ${item.startingBid.toFixed(2)}
                        </span>
                    </div>

                    <div className="detail-card">
                        <span className="detail-label">Current Bid</span>
                        <span className="detail-value">
                            $
                            {(highestBid?.amount ?? item.startingBid).toFixed(2)}
                        </span>
                    </div>
                </div>

                <div className="bid-section">
                    <h2 className="bid-title">Place Your Bid</h2>

                    <p className="bid-subtitle">
                        Enter an amount higher than the current highest bid.
                    </p>

                    <PlaceBid itemId={item.id} />
                </div>
            </div>
        </div>
    );
}