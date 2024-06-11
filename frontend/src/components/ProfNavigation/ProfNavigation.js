import React, { useState } from "react";
import { Navbar, NavItem, NavLink } from "reactstrap";
import { Link, useLocation } from "react-router-dom";
import "./ProfNavigation.css";

const ProfNavigation = () => {
    const location = useLocation();
    const [dropdownOpen, setDropdownOpen] = useState(false);

    const toggleDropdown = () => setDropdownOpen(!dropdownOpen);

    return (
        <header>
            <Navbar className="navbar">
                <ul>
                    <img src="/logo-white.png" className="logo" alt="Logo" />
                    <NavItem>
                        <NavLink
                            tag={Link}
                            className={`text-light ${location.pathname === "/programs" ? "active" : ""}`}
                            to="/programs"
                        >
                            Programs
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink
                            tag={Link}
                            className={`text-light ${location.pathname === "/prof-mentorship" ? "active" : ""}`}
                            to="/prof-mentorship"
                        >
                            Mentorship
                        </NavLink>
                    </NavItem>
                    <NavItem
                        className="profile-dropdown"
                        onMouseEnter={toggleDropdown}
                        onMouseLeave={toggleDropdown}
                    >
                        <NavLink tag={Link} className={`text-light ${location.pathname === "/profProfile" ? "active" : ""} ${dropdownOpen ? "active" : ""}`} to="#">
                            Profile
                        </NavLink>
                        {dropdownOpen && (
                            <div className="dropdown-menu">
                                <NavLink tag={Link} className="dropdown-item" to="/profProfile">
                                    View Profile
                                </NavLink>
                                <NavLink tag={Link} className="dropdown-item" to="/">
                                    Logout
                                </NavLink>
                            </div>
                        )}
                    </NavItem>
                </ul>
            </Navbar>
        </header>
    );
};

export default ProfNavigation;
