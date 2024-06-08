import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Programs } from './components/Programs/Programs';
import Login from './components/Login/Login';
import {Recommendation} from "./components/Recommendation/Recommendation";
import {Mentorship} from "./components/Mentorship/Mentorship";
import ProfMentorship from "./components/ProfMentorship/ProfMentorship";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/programs" element={<Programs />} />
      <Route path="/recommendation" element={<Recommendation />} />
      <Route path="/login" element={<Login />} />
      <Route path="/mentorship" element={<Mentorship />} />
      <Route path="/prof-mentorship" element={<ProfMentorship />} />
      {/* Add more routes if needed */}
    </Routes>
  );
};  

export default AppRoutes;
