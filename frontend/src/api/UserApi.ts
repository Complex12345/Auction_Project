import axios from "axios";

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

export const updateUsername = async (oldUsername: string, newUsername: string) => {
    return apiWithAuth.patch("/user/v1/update/username", { oldUsername, newUsername });
};

export const updatePassword = async (username: string, newPassword: string) => {
    return apiWithAuth.patch("/user/v1/update/password", { username, newPassword });
};
export default api;
