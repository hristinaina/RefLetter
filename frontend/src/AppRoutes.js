import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Programs } from './components/Programs/Programs';
import Login from './components/Login/Login';
import {Recommendation} from "./components/Recommendation/Recommendation";
import {Mentorship} from "./components/Mentorship/Mentorship";
import ProfMentorship from "./components/ProfMentorship/ProfMentorship";
import {MainRegister} from "./components/Registration/MainRegister";
import StudentProfile from './components/Profile/StudentProfile';
import ProfessorProfile from './components/Profile/ProfessorProfile';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/programs" element={<Programs />} />
      <Route path="/recommendation" element={<Recommendation />} />
      <Route path="/login" element={<Login />} />
      <Route path="/mentorship" element={<Mentorship />} />
      <Route path="/prof-mentorship" element={<ProfMentorship />} />
      <Route path="/reg" element={<MainRegister />} />
      <Route path="/studentProfile" element={<StudentProfile />} />
      <Route path="/profProfile" element={<ProfessorProfile />} />
      {/* Add more routes if needed */}
    </Routes>
  );
};  

export default AppRoutes;
