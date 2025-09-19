import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route} from 'react-router-dom';
import { RegistrationPage } from './components/RegistrationPage';
import './style.css';
import {NavigationBar} from "./components/NavigationBar.tsx";
import {LoginPage} from "./components/LoginPage.tsx";
import {HomePage} from "./components/HomePage.tsx";

const rootElement = document.getElementById('app');

if (rootElement) {
    ReactDOM.createRoot(rootElement).render(
        <React.StrictMode>
            <BrowserRouter>
                <NavigationBar/>
                <Routes>
                    <Route path={"/"} element={<HomePage/>}/>
                    <Route path={"/register"} element={<RegistrationPage/>}/>
                    <Route path={"/login"} element={<LoginPage/>}/>
                </Routes>
            </BrowserRouter>
        </React.StrictMode>
    );
} else {
    console.error("Failed to find the root element with id 'app'");
}
