import axios from "axios";
import type { Item } from "../types/Item";
import type { Bid } from "../types/Bid";

const BASE_URL = "http://localhost:8080";

const apiWithAuth = axios.create({
    baseURL: BASE_URL,
});

apiWithAuth.interceptors.request.use(
    config => {
        const token = localStorage.getItem("jwtToken");
        console.log(token);
        console.log(token?.length);

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    error => Promise.reject(error)
);

export const getTrendingItems = async (): Promise<Item[]> => {
    const response = await apiWithAuth.get<Item[]>("/item/v1/trending");
    return response.data;
};

export const getItem = async (id: number): Promise<Item> => {
    const response = await apiWithAuth.get<Item>(`/item/v1/${id}`);
    return response.data;
};

export const getMyItems = async (): Promise<Item[]> => {
    const response = await apiWithAuth.get<Item[]>("/item/v1/myItems");
    return response.data;
};

export const createItem = async (formData: FormData): Promise<Item> => {
    const response = await apiWithAuth.put<Item>(
        "/item/v1/create",
        formData
    );

    return response.data;
};

export const updateItemName = async (
    id: number,
    name: string
): Promise<Item> => {
    const response = await apiWithAuth.patch<Item>(
        `/item/v1/updateName/${id}`,
        name,
        {
            headers: {
                "Content-Type": "application/json",
            },
        }
    );

    return response.data;
};

export const updateItemDescription = async (
    id: number,
    description: string
): Promise<Item> => {
    const response = await apiWithAuth.patch<Item>(
        `/item/v1/updateDescription/${id}`,
        description,
        {
            headers: {
                "Content-Type": "application/json",
            },
        }
    );

    return response.data;
};

export const getHighestBid = async (id: number): Promise<Bid | null> => {
    try {
        const response = await apiWithAuth.get<Bid>(`/item/v1/maxBid/${id}`);
        return response.data;
    } catch {
        return null;
    }
};