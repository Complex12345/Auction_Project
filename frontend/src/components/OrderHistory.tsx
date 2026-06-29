

type Order = {
    id: number;
    itemName: string;
    finalPrice: number;
    seller: string;
    purchaseDate: string;
};

export function OrderHistoryPage() {
    const orders: Order[] = [
        {
            id: 1,
            itemName: "Gaming Laptop",
            finalPrice: 1200.00,
            seller: "John",
            purchaseDate: "2026-06-20",
        },
        {
            id: 2,
            itemName: "Mechanical Keyboard",
            finalPrice: 95.50,
            seller: "Sarah",
            purchaseDate: "2026-06-15",
        },
        {
            id: 3,
            itemName: "27-inch Monitor",
            finalPrice: 280.00,
            seller: "Mike",
            purchaseDate: "2026-06-10",
        },
    ];

    return (
        <div className="order-history-container">
            <h1>Order History</h1>

            {orders.length === 0 ? (
                <p>You have not purchased any items.</p>
            ) : (
                <table className="order-history-table">
                    <thead>
                        <tr>
                            <th>Item</th>
                            <th>Seller</th>
                            <th>Final Price</th>
                            <th>Purchase Date</th>
                        </tr>
                    </thead>

                    <tbody>
                        {orders.map((order) => (
                            <tr key={order.id}>
                                <td>{order.itemName}</td>
                                <td>{order.seller}</td>
                                <td>${order.finalPrice.toFixed(2)}</td>
                                <td>{order.purchaseDate}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}