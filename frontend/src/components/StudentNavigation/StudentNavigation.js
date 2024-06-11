import React, { useState } from "react";
import { Navbar, NavItem, NavLink } from "reactstrap";
import { Link, useLocation } from "react-router-dom";
import "./StudentNavigation.css";
import authService from "../../services/AuthService";

const StudentNavigation = () => {
  const location = useLocation();
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const handleLogout = () => {
    authService.logout();
  };

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
              className={`text-light ${location.pathname === "/recommendation" ? "active" : ""}`}
              to="/recommendation"
            >
              Recommendation
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              tag={Link}
              className={`text-light ${location.pathname === "/mentorship" ? "active" : ""}`}
              to="/mentorship"
            >
              Mentorship
            </NavLink>
          </NavItem>
          <NavItem
            className="profile-dropdown"
            onMouseEnter={toggleDropdown}
            onMouseLeave={toggleDropdown}
          >
            <NavLink tag={Link} className={`text-light ${location.pathname === "/studentProfile" ? "active" : ""} ${dropdownOpen ? "active" : ""}`} to="#">
              Profile
            </NavLink>
            {dropdownOpen && (
              <div className="dropdown-menu">
                <NavLink tag={Link} className="dropdown-item" to="/studentProfile">
                  View Profile
                </NavLink>
                <NavLink
                    tag={Link}
                    className="dropdown-item"
                    to="/login"
                    onClick={handleLogout}
                >
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

export default StudentNavigation;
