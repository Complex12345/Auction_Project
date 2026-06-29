import { useState } from "react";
import { Link } from "react-router-dom";
import { MyBids } from "./MyBids";
import { MyItems } from "./MyItems";
import '../css/Dashboard.css';

export function Dashboard() {
    const [selectedTab, setSelectedTab] = useState<"bids" | "items">("bids");

    return (
        <div className="dashboard-container">

            <div className="dashboard-header">

                <button
                    className={selectedTab === "bids" ? "active-tab" : ""}
                    onClick={() => setSelectedTab("bids")}
                >
                    Your Bids
                </button>

                <div className="auction-items-header">

                    <button
                        className={selectedTab === "items" ? "active-tab" : ""}
                        onClick={() => setSelectedTab("items")}
                    >
                        Auction Items
                    </button>

                    {selectedTab === "items" && (
                        <Link to="/uploadItem">
                            <button className="upload-button">
                                Upload Item
                            </button>
                        </Link>
                    )}

                </div>

            </div>

            <div className="dashboard-content">

                {selectedTab === "bids" && <MyBids />}

                {selectedTab === "items" && <MyItems />}

            </div>

        </div>
    );
}