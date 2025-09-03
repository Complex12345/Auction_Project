import React from "react";
import {useState} from "react"
import {registerUser} from "../api/UserApi.ts";

export function RegistrationPage(){
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("");
    const [message, setMessage]   = useState("");

    const sendRequest = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await registerUser({username, email, password})
                setMessage("Registration Successful")
        }
        catch (error) {
            setMessage("Registration Failed")
        }
    };


    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={sendRequest}>
                <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Username" />
                <input value={email}    onChange={(e) => setEmail(e.target.value)} placeholder="Email" type="email" />
                <input value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" type="password" />
                <button type="submit">Sign Up</button>
            </form>
            <p>{message}</p>
        </div>
    )
}