import React, {useState} from "react";
import {loginUser} from "../api/UserApi.ts";


export function LoginPage(){
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("");
    const [message, setMessage]   = useState("");
    const [isLoading, setIsLoading] = useState(false)

    const sendRequest = async (e: React.FormEvent) => {
        e.preventDefault();
        if(!email || !password){
            setMessage("All fields are required")
            return;
        }

        setIsLoading(true)
        try {
            await loginUser({email, password})
            setMessage("Login in Successful Redirecting...")
            console.log("registered user with email: " + email)
        }
        catch (error) {
            setMessage("Login in failed")
        }
        setIsLoading(false)
    };


    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={sendRequest}>
                <input value={email}    onChange={(e) => setEmail(e.target.value)} placeholder="Email" type="email" />
                <input value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" type="password" />
                <button type="submit">{isLoading ? "Logging In..." : "Logged In"}</button>
            </form>
            <p>{message}</p>
        </div>
    )

}