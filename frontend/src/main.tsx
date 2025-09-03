import React from 'react';
import ReactDOM from 'react-dom/client';
import { RegistrationPage } from './components/RegistrationPage';
import './style.css';

const rootElement = document.getElementById('app');

if (rootElement) {
    ReactDOM.createRoot(rootElement).render(
        <React.StrictMode>
            <RegistrationPage />
        </React.StrictMode>
    );
} else {
    console.error("Failed to find the root element with id 'app'");
}
