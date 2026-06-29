import { useState } from "react";
import { createItem } from "../api/ItemApi";
import "../css/UploadItem.css";

export function UploadItem() {

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [category, setCategory] = useState("");
    const [condition, setCondition] = useState("");
    const [startingBid, setStartingBid] = useState("");
    const [auctionEndTime, setAuctionEndTime] = useState("");
    const [image, setImage] = useState<File | null>(null);

    const [message, setMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const uploadItem = async (e: React.FormEvent) => {
        e.preventDefault();

        if (
            !name ||
            !description ||
            !category ||
            !condition ||
            !startingBid ||
            !auctionEndTime ||
            !image
        ) {
            setMessage("All fields are required.");
            return;
        }

        const formData = new FormData();

        formData.append("image", image);
        formData.append("name", name);
        formData.append("description", description);
        formData.append("category", category);
        formData.append("condition", condition);
        formData.append("startingBid", startingBid);
        formData.append("auctionEndTime", auctionEndTime);

        setIsLoading(true);
        setMessage("");

        try {
            await createItem(formData);

            setMessage("Item uploaded successfully!");

            setName("");
            setDescription("");
            setCategory("");
            setCondition("");
            setStartingBid("");
            setAuctionEndTime("");
            setImage(null);

            const fileInput = document.getElementById(
                "item-image"
            ) as HTMLInputElement;

            if (fileInput) {
                fileInput.value = "";
            }

        } catch (error) {
            console.error(error);
            setMessage("Failed to upload item.");
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="upload-container">
            <div className="upload-card">

                <h2>Upload Auction Item</h2>

                <form className="upload-form" onSubmit={uploadItem}>

                    <input
                        placeholder="Item Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />

                    <textarea
                        placeholder="Description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />

                    <input
                        placeholder="Category"
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                        required
                    />

                    <input
                        placeholder="Condition"
                        value={condition}
                        onChange={(e) => setCondition(e.target.value)}
                        required
                    />

                    <input
                        type="number"
                        min="0"
                        step="0.01"
                        placeholder="Starting Bid"
                        value={startingBid}
                        onChange={(e) => setStartingBid(e.target.value)}
                        required
                    />

                    <input
                        type="datetime-local"
                        value={auctionEndTime}
                        onChange={(e) => setAuctionEndTime(e.target.value)}
                        required
                    />

                    <input
                        id="item-image"
                        type="file"
                        accept="image/*"
                        onChange={(e) => {
                            if (e.target.files && e.target.files.length > 0) {
                                setImage(e.target.files[0]);
                            }
                        }}
                        required
                    />

                    <button type="submit" disabled={isLoading}>
                        {isLoading ? "Uploading..." : "Upload Item"}
                    </button>

                    {message && <p className="upload-message">{message}</p>}

                </form>

            </div>
        </div>
    );
}