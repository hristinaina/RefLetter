import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Home } from './components/Home';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/home" />} />
      <Route path="/home" element={<Home />} />
      {/* Add more routes if needed */}
    </Routes>
  );
};  

export default AppRoutes;
