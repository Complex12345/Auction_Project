import { useEffect, useState } from "react";
import { getBidHistory } from "../api/BidApi";
import type { UserBids } from "../types/UserBids";

export function MyBids() {

    const [items, setItems] = useState<UserBids[]>([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState("");

    useEffect(() => {
        loadBidHistory();
    }, []);

    const loadBidHistory = async () => {
        setLoading(true);
        setMessage("");

        try {
            const bids = await getBidHistory();
            setItems(bids);
        } catch (error) {
            console.error(error);
            setMessage("Failed to load your bid history.");
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
        return <p>You haven't placed any bids yet.</p>;
    }

    return (
        <>
            {items.map((bid) => (
                <div key={bid.id} className="auction-item-card">

                    <div className="item-main-details">
                        <p className="item-title">{bid.itemName}</p>
                    </div>

                    <div className="item-details-grid">

                        <div className="detail-group">
                            <p className="detail-label">Starting Bid</p>
                            <p className="detail-value">
                                ${bid.startingBidPrice.toFixed(2)}
                            </p>
                        </div>

                        <div className="detail-group">
                            <p className="detail-label">Final Price</p>
                            <p className="detail-value">
                                ${bid.finalPrice.toFixed(2)}
                            </p>
                        </div>

                        <div className="detail-group">
                            <p className="detail-label">Highest Bidder</p>
                            <p className="detail-value">
                                {bid.highestBidder}
                            </p>
                        </div>

                        <div className="detail-group">
                            <p className="detail-label">Date Listed</p>
                            <p className="detail-value">
                                {new Date(bid.dateListed).toLocaleDateString()}
                            </p>
                        </div>

                    </div>

                </div>
            ))}
        </>
    );
}