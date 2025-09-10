import React from "react";
import {useState} from "react"
import {registerUser} from "../api/UserApi.ts";
import '../css/RegistrationPage.css';

export function RegistrationPage(): React.ReactElement{
    const [username, setUsername]= useState<string>("");
    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("");
    const [message, setMessage]   = useState<string>("");
    const [isLoading, setIsLoading] = useState<boolean>(false)

    const sendRequest = async (e: React.FormEvent) => {
        e.preventDefault();
        if(!username || !email || !password){
            setMessage("All fields are required")
            return;
        }

        if(username.length < 3 && username.length > 20){
            setMessage("Username must be between 3 and 20")
            return;
        }

        if(password.length < 6 && password.length > 25){
            setMessage("Password must be between 3 and 20")
            return;
        }



        setIsLoading(true)
        try {
            await registerUser({username, email, password})
            setMessage("Registration Successful")
            console.log("registered user with,\nname: " + username + "\nemail: " + email)
        }
        catch (error) {
            setMessage("Registration Failed")
        }
        setIsLoading(false)
    };


    return (
        <div>
            <div className={"container"}>
                <h2>Register</h2>
                <form className={"form-layout"} onSubmit={sendRequest}>
                    <input className={"container-input"} value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Your username" />
                    <input className={"container-input"} value={email}    onChange={(e) => setEmail(e.target.value)} placeholder="Your email" type="email" />
                    <input className={"container-input"} value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Your password" type="password" />
                    <button className={"container-button"} type="submit">{isLoading ? "Signing Up..." : "Sign Up"}</button>
                </form>
                <p>{message}</p>
            </div>

        </div>
    )
}