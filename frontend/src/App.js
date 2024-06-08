import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AppRoutes from './AppRoutes';
import { ThemeProvider } from '@mui/material/styles';
import lightTheme from './themes/lightTheme';



function App() {
  return (
    <ThemeProvider theme={lightTheme}>
      <Router>
        <AppRoutes />
      </Router>
  </ThemeProvider>
  );
}

export default App;
