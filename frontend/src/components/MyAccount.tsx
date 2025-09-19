import React, { useState } from 'react';
import { updateUsername, updatePassword } from '../api/UserApi.ts';

export function MyAccountPage() {
    const [oldUsername, setOldUsername] = useState("");
    const [newUsername, setNewUsername] = useState("");
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleUpdateUsername = async (e: React.FormEvent) => {
        e.preventDefault();
        setMessage('');
        try {
            await updateUsername(oldUsername, newUsername);
            setMessage('Username updated successfully!');
            setOldUsername("");
            setNewUsername("");
        } catch (error) {
            setMessage('Failed to update username. Please try again.');
        }
    };

    const handleUpdatePassword = async (e: React.FormEvent) => {
        e.preventDefault();
        setMessage('');
        try {
            // Note: The backend updatePassword method requires the username
            const currentUsername = ""; // You need to get the current username from context or a global state
            await updatePassword(currentUsername, newPassword);
            setMessage('Password updated successfully!');
            setOldPassword("");
            setNewPassword("");
        } catch (error) {
            setMessage('Failed to update password. Please try again.');
        }
    };

    return (
        <div>
            <h2>My Account</h2>
            {message && <p>{message}</p>}

            <h3>Update Username</h3>
            <form onSubmit={handleUpdateUsername}>
                <input
                    value={oldUsername}
                    onChange={(e) => setOldUsername(e.target.value)}
                    placeholder="Old Username"
                    required
                />
                <input
                    value={newUsername}
                    onChange={(e) => setNewUsername(e.target.value)}
                    placeholder="New Username"
                    required
                />
                <button type="submit">Update Username</button>
            </form>

            <h3>Update Password</h3>
            <form onSubmit={handleUpdatePassword}>
                <input
                    value={oldPassword}
                    onChange={(e) => setOldPassword(e.target.value)}
                    placeholder="Old Password"
                    type="password"
                    required
                />
                <input
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    placeholder="New Password"
                    type="password"
                    required
                />
                <button type="submit">Update Password</button>
            </form>
        </div>
    );
}