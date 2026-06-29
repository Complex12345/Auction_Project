import React, { useState } from "react";
import { updateUsername, updatePassword } from "../api/UserApi.ts";

export function AccountSettings() {
    const [newUsername, setNewUsername] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleUpdateUsername = async (e: React.FormEvent) => {
        e.preventDefault();
        setMessage("");

        try {
            await updateUsername(newUsername);

            setMessage("Username updated successfully!");
            setNewUsername("");
        } catch (error) {
            setMessage("Failed to update username.");
        }
    };

    const handleUpdatePassword = async (e: React.FormEvent) => {
        e.preventDefault();
        setMessage("");

        try {
            await updatePassword(newPassword);

            setMessage("Password updated successfully!");
            setNewPassword("");
        } catch (error) {
            setMessage("Failed to update password.");
        }
    };

    return (
        <div className="container">
            <h2>Account Settings</h2>

            {message && <p>{message}</p>}

            <div className="form-layout">
                <h3>Update Username</h3>

                <form onSubmit={handleUpdateUsername}>
                    <input
                        type="text"
                        placeholder="New Username"
                        value={newUsername}
                        onChange={(e) => setNewUsername(e.target.value)}
                        required
                    />

                    <button type="submit">
                        Update Username
                    </button>
                </form>

                <h3>Update Password</h3>

                <form onSubmit={handleUpdatePassword}>
                    <input
                        type="password"
                        placeholder="New Password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        required
                    />

                    <button type="submit">
                        Update Password
                    </button>
                </form>
            </div>
        </div>
    );
}