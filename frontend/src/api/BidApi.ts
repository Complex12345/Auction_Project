import axios from "axios";
import type { UserBids } from "../types/UserBids";

const BASE_URL = "http://localhost:8080";

const api = axios.create({
    baseURL: BASE_URL,
});

api.interceptors.request.use(config => {
    const token = localStorage.getItem("jwtToken");

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export const placeBid = (data: {
    itemId: number;
    bidderAmount: number;
}) =>
    api.post("/item/v1/placeBid", {
        itemId: data.itemId,
        bidderId: null,
        bidderAmount: data.bidderAmount
    });

export const getBidHistory = async (): Promise<UserBids[]> => {
    const response = await api.get<UserBids[]>("/item/v1/getBidHistory");
    return response.data;
};

export const getHighestBid = async (itemId: number) => {
    const response = await api.get(`/item/v1/maxBid/${itemId}`);
    return response.data;
};

export const removeBid = async (bidderId: string) => {
    const response = await api.delete(`/item/v1/removeBid/${bidderId}`);
    return response.data;
};