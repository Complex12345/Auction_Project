import { useState } from "react";
import { placeBid } from "../api/BidApi";

type Props = {
    itemId: number;
};

export function PlaceBid({ itemId }: Props) {
    const [amount, setAmount] = useState("");

    const submitBid = async () => {
        if (!amount) {
            return;
        }

        try {
            await placeBid({
                itemId,
                bidderAmount: Number(amount),
            });

            alert("Bid placed successfully!");
            setAmount("");
        } catch (error) {
            console.error(error);
            alert("Failed to place bid.");
        }
    };

    return (
        <form
            className="bid-form"
            onSubmit={(e) => {
                e.preventDefault();
                submitBid();
            }}
        >
            <input
                className="bid-input"
                type="number"
                min="0"
                step="0.01"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                placeholder="Enter your bid"
            />

            <button
                className="bid-button"
                type="submit"
            >
                Place Bid
            </button>
        </form>
    );
}