import axios from "axios";

const api = axios.create({
    baseURL: "http://backend:8080",
    headers: {
        "Content-Type": "application/json",
    },
});

export const registerUser = (data: {
    username: string;
    email: string;
    password: string;
}) => api.post("/user/v1/signup", data);

export const loginUser = (data: {
    email: string;
    password: string;
}) => api.post("/user/v1/login", data);

export default api;
