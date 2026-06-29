import '../css/NavigationBar.css';
import { Link } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import { useLocation } from "react-router-dom";

export function NavigationBar() {
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
    const location = useLocation();

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoggedIn(!!token);
    }, [location]);


    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(event.target.value);
    };

    const handleSearchSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log('Searching for:', searchTerm);
    };

    return (
        <nav className="navbar">
            <div className="nav-group left-group">
                <Link to="/" className="nav-link">Home</Link>
                <Link to="/about" className="nav-link">About</Link>
            </div>

            {/* <form className="search-form" onSubmit={handleSearchSubmit}>
                <input
                    type="text"
                    placeholder="Search..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
                <button type="submit">Search</button>
            </form> */}

            <div className="nav-group right-group">
                {isLoggedIn &&
                    <Link to="/dashboard" className="nav-link">DashBoard</Link>
                }
                {/* <Link to="/contact" className="nav-link">Contact</Link> */}
                {isLoggedIn ?
                    <Link to="/accountSettings" className="nav-link">Account</Link>
                    :
                    <Link to="/register" className="nav-link">Register</Link>
                }
            </div>
        </nav>
    )
}