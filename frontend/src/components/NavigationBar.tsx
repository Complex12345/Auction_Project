import '../css/NavigationBar.css';
import {Link} from "react-router-dom";
import React, {useState} from "react";

export function NavigationBar() {
    const [searchTerm, setSearchTerm] = useState<string>('');

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

            <form className="search-form" onSubmit={handleSearchSubmit}>
                <input
                    type="text"
                    placeholder="Search..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
                <button type="submit">Search</button>
            </form>

            <div className="nav-group right-group">
                <Link to="/contact" className="nav-link">Contact</Link>
                <Link to="/login" className="nav-link">Login</Link>
            </div>
        </nav>
    )
}