import React, {useState} from 'react';
import ProfessorRegistration from './ProfessorRegistration';
import StudentRegistration from './StudentRegistration';
import {Switch, FormControlLabel} from '@mui/material';
import '../Login/Login.css';
import {ThemeProvider} from "@emotion/react";
import lightTheme from "../../themes/lightTheme";
import TextField from "@mui/material/TextField";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import Visibility from "@mui/icons-material/Visibility";
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";
import Snackbar from "@mui/material/Snackbar";

export function MainRegister() {
    const [isProfessor, setIsProfessor] = useState(false);

    const handleSwitchChange = (event) => {
        setIsProfessor(event.target.checked);
    };


    return (
        <ThemeProvider theme={lightTheme}>
            <div className="background">
                <img src="/logo-white.png" className="top-right-logo" alt="Logo"/>
                <div className="left-side" >
                    <p className="title-login">Register</p>
                    <FormControlLabel
                        control={<Switch checked={isProfessor} onChange={handleSwitchChange}/>}
                        label="Register as a professor"
                    />
                    {isProfessor ? <ProfessorRegistration/> : <StudentRegistration/>}
                    <Link to="/login" style={{textDecoration: "none"}}>
                        <p className="reg" variant="contained" style={{textTransform: 'none'}}>Already have an acoount
                        LOG IN</p>
                    </Link>
                </div>
                <div className="right-side">
                    <img src="/edu.gif" className="edu-image" alt="education image"/>
                    <p className='title'>Welcome to <span style={{color: "var(--background-blue)"}}> RefLetter! </span>
                    </p>
                    <p className='text'>Your Compass to Higher Education</p>
                </div>
            </div>
        </ThemeProvider>
    );
}