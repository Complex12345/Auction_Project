import React, {useState} from "react";
import {loginUser} from "../api/UserApi.ts";
import '../css/RegistrationAndLoginPage.css'
import { useNavigate } from "react-router-dom";

export function LoginPage(){
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("");
    const [message, setMessage]   = useState("");
    const [isLoading, setIsLoading] = useState(false)
    const navigate = useNavigate();

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
            console.log("Logging in user with email: " + email)
            console.log("JWT token is:" + localStorage.getItem("jwtToken"))
            await delay(1000);
            navigate("/")

        }
        catch (error) {
            setMessage("Login in failed")
        }
        setIsLoading(false)
    };


    return (
        <div>
            <div className={"container"}>
                <h2>Login</h2>
                <form className={"form-layout"} onSubmit={sendRequest}>
                    <input className={"container-input"} value={email}    onChange={(e) => setEmail(e.target.value)} placeholder="Your email" type="email" />
                    <input className={"container-input"} value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Your password" type="password" />
                    <button className={"container-button"} type="submit">{isLoading ? "Logging In..." : "Login In"}</button>
                </form>
                <p>{message}</p>
            </div>
        </div>
    )

}