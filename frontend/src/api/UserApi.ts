import axios from "axios";
import type { UserBids } from "../types/UserBids";


// @ts-ignore
const BASE_URL_DEV = "http://localhost:8080"
// @ts-ignore
const BASE_URL_PROD = "http://backend:8080"

const api = axios.create({
    baseURL: BASE_URL_DEV,
    headers: {
        "Content-Type": "application/json",
    },
});

const apiWithAuth = axios.create({
    baseURL: BASE_URL_DEV,
    headers: {
        "Content-Type": "application/json",
    },
})

apiWithAuth.interceptors.request.use(
    config => {
        const token = localStorage.getItem("jwtToken");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

export const registerUser = (data: {
    username: string;
    email: string;
    password: string;
}) => api.post("/user/v1/signup", data);

export const loginUser = async (data: {
    email: string;
    password: string;
}) => {
    const response = await api.post("/user/v1/login", data);
    const token = response.data;
    localStorage.setItem("jwtToken", token);
    return response;
};

export const updateUsername = (newUsername: string) => {
    return apiWithAuth.patch("/user/v1/update/username", {
        newUsername
    });
};

export const updatePassword = (newPassword: string) => {
    return apiWithAuth.patch("/user/v1/update/password", {
        newPassword
    });
};


export const getUserHistory = async (): Promise<UserBids[]> => {
    const response = await apiWithAuth.get<UserBids[]>("/bids/v1/bidhistory");
    return response.data;
};

export const getBidHistory = async (): Promise<UserBids[]> => {
    const response = await apiWithAuth.get<UserBids[]>("/bids/v1/bidhistory");
    return response.data;
};

export const checkEmailExists = async (email: string): Promise<boolean> => {
    const response = await api.get("/user/v1/findEmail", {
        data: email
    });

    return response.data;
};

export const checkUsernameExists = async (username: string): Promise<boolean> => {
    const response = await api.get("/user/v1/findUsername", {
        data: username
    });

    return response.data;
};

export const logoutUser = () => {
    localStorage.removeItem("jwtToken");
};

export const isLoggedIn = (): boolean => {
    return localStorage.getItem("jwtToken") !== null;
};

export const getJwtToken = (): string | null => {
    return localStorage.getItem("jwtToken");
};


export default api;
