import React, {useState} from 'react';
import ProfessorRegistration from './ProfessorRegistration';
import StudentRegistration from './StudentRegistration';
import {Switch, FormControlLabel} from '@mui/material';
import './Registration.css';
import {ThemeProvider} from "@emotion/react";
import lightTheme from "../../themes/lightTheme";
import {Link} from "react-router-dom";
import '../Login/Login.css';

export function MainRegister() {
    const [isProfessor, setIsProfessor] = useState(false);

    const handleSwitchChange = (event) => {
        setIsProfessor(event.target.checked);
    };


    return (
        <ThemeProvider theme={lightTheme}>
            <div id='reg-card'>
                <img src="/logo-white.png" className="top-right-logo" alt="Logo"/>
                <div className="contain-reg" >
                    <p className="title-reg">Register</p>
                    <FormControlLabel
                        control={<Switch checked={isProfessor} onChange={handleSwitchChange}/>}
                        label="Register as a professor"
                        style={{color: "black"}}
                    />
                    {isProfessor ? <ProfessorRegistration theme={lightTheme}/> : <StudentRegistration  theme={lightTheme}
                  updated={false}/>}
                    <Link to="/login" style={{textDecoration: "none"}}>
                        <p className="reg" variant="contained" style={{textTransform: 'none'}}>Already have an acoount
                        LOG IN</p>
                    </Link>
                </div>
            </div>
        </ThemeProvider>
    );
}