import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Programs } from './components/Programs';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/programs" />} />
      <Route path="/programs" element={<Programs />} />
      {/* Add more routes if needed */}
    </Routes>
  );
};  

export default AppRoutes;
