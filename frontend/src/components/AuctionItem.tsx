import "../css/AuctionItem.css";
import { Link } from "react-router-dom";
import type {Item} from "../types/Item.ts";

type AuctionItemProps = {
    item: Item;
};

export function AuctionItem({ item }: AuctionItemProps) {
    const itemUrl = `/items/${item.id}`;

    return (
        <Link to={itemUrl} className="auction-item-link">
            <div className="auction-item-card">
                <div className="item-main-details">
                    <p className="item-title">{item.name}</p>
                    <img className="item-image" src={item.imageURL} alt={item.name} />
                    <p className="item-description">{item.description}</p>
                </div>

                <div className="item-details-grid">
                    <div className="detail-group">
                        <p className="detail-label">{item.category}</p>
                        <p className="detail-value">{item.condition}</p>
                    </div>
                    <div className="detail-group">
                        <p className="detail-label">{item.auctionEndTime}</p>
                        <p className="detail-value">{item.currentBid}</p>
                    </div>
                </div>
            </div>
        </Link>
    );
}