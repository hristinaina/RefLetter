import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Programs } from './components/Programs/Programs';
import Login from './components/Login/Login';
import {Recommendation} from "./components/Recommendation/Recommendation";
import {Mentorship} from "./components/Mentorship/Mentorship";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/programs" element={<Programs />} />
      <Route path="/recommendation" element={<Recommendation />} />
      <Route path="/login" element={<Login />} />
      <Route path="/mentorship" element={<Mentorship />} />
      {/* Add more routes if needed */}
    </Routes>
  );
};  

export default AppRoutes;
