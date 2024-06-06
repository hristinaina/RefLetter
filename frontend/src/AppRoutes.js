import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Programs } from './components/Programs';
import Login from './components/Login/Login';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/programs" element={<Programs />} />
      <Route path="/login" element={<Login />} />
      {/* Add more routes if needed */}
    </Routes>
  );
};  

export default AppRoutes;
