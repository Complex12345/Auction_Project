import "../css/AuctionItem.css";
import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import type { Item } from "../types/Item";

type AuctionItemProps = {
    item: Item;
};

export function AuctionItem({ item }: AuctionItemProps) {
    const itemUrl = `/items/${item.id}`;
    const auctionEndTime = new Date(item.auctionEndTime);

    const getCountdown = () => {
        const total = auctionEndTime.getTime() - Date.now();

        if (total <= 0) {
            return "Auction Ended";
        }

        const seconds = Math.floor((total / 1000) % 60);
        const minutes = Math.floor((total / (1000 * 60)) % 60);
        const hours = Math.floor((total / (1000 * 60 * 60)) % 24);
        const days = Math.floor(total / (1000 * 60 * 60 * 24));

        return `${days}d ${hours}h ${minutes}m ${seconds}s`;
    };

    const [countdown, setCountdown] = useState(getCountdown());

    useEffect(() => {
        const timer = setInterval(() => {
            setCountdown(getCountdown());
        }, 1000);

        return () => clearInterval(timer);
    }, []);

    return (
        <Link to={itemUrl} className="auction-item-link">
            <div className="auction-item-card">

                <div className="item-main-details">
                    <p className="item-title">{item.name}</p>

                    <img
                        className="item-image"
                        src={`data:image/jpeg;base64,${item.image}`}
                        alt={item.name}
                    />

                    <p className="item-description">{item.description}</p>
                </div>

                <div className="item-details-grid">
                    <div className="detail-group">
                        <p className="detail-label">{item.category}</p>
                        <p className="detail-value">{item.condition}</p>
                    </div>

                    <div className="detail-group">
                        <p className="detail-label">Time Remaining</p>
                        <p className="detail-value">{countdown}</p>
                    </div>
                </div>

            </div>
        </Link>
    );
}