import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import "./style.css";

import { NavigationBar } from "./components/NavigationBar";
import { HomePage } from "./components/HomePage";
import { RegistrationPage } from "./components/RegistrationPage";
import { LoginPage } from "./components/LoginPage";
import { Dashboard } from "./components/Dashboard";
import { UploadItem } from "./components/UploadItem";
import { MyItems } from "./components/MyItems";
import { MyBids } from "./components/MyBids";
import { AccountSettings } from "./components/AccountSettings";
import { MyAccountPage } from "./components/MyAccount";
import { OrderHistoryPage } from "./components/OrderHistory";
import { ItemDetailsPage } from "./components/ItemDetailsPage";

const rootElement = document.getElementById("app");

if (rootElement) {
    ReactDOM.createRoot(rootElement).render(
        <React.StrictMode>
            <BrowserRouter>

                <NavigationBar />

                <Routes>

                    <Route path="/" element={<HomePage />} />

                    <Route path="/items/:id" element={<ItemDetailsPage />} />

                    <Route path="/register" element={<RegistrationPage />} />
                    <Route path="/login" element={<LoginPage />} />

                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/dashboard/bids" element={<MyBids />} />
                    <Route path="/dashboard/items" element={<MyItems />} />

                    <Route path="/uploadItem" element={<UploadItem />} />

                    <Route path="/myAccount" element={<MyAccountPage />} />
                    <Route path="/accountSettings" element={<AccountSettings />} />
                    <Route path="/orderHistory" element={<OrderHistoryPage />} />

                </Routes>

            </BrowserRouter>
        </React.StrictMode>
    );
} else {
    console.error("Failed to find the root element with id 'app'");
}