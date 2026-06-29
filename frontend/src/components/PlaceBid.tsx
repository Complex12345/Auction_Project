import { useState } from "react";

type Props = {
    itemId: number;
};

export function PlaceBid({ itemId }: Props) {
    const [amount, setAmount] = useState("");

    const submitBid = async () => {
        console.log("Item:", itemId);
        console.log("Bid:", amount);

        // TODO: call your bid API
    };

    return (
        <div>
            <h2>Place Bid</h2>

            <input
                type="number"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                placeholder="Enter bid"
            />

            <button onClick={submitBid}>
                Place Bid
            </button>
        </div>
    );
}