import React, {useState} from 'react';
import {TextField, Button, Snackbar} from '@mui/material'; // Snackbar is now imported
import authService from "../../services/AuthService";
import {useNavigate} from "react-router-dom";
import darkTheme from "../../themes/darkTheme";
import {ThemeProvider} from "@mui/material/styles";
import '../Login/Login.css';
import lightTheme from '../../themes/lightTheme';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import CloseIcon from '@mui/icons-material/Close';

const ProfessorRegistration = () => {
    const navigate = useNavigate();

    const [professor, setProfessor] = useState({
        university: '',
        name: '',
        surname: '',
        email: '',
        password: ''
    });

    const [confirmPassword, setConfirmPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [open, setOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');

    const handleChange = (e) => {
        setProfessor({...professor, [e.target.name]: e.target.value});
    };

    const handleConfirmPasswordChange = (e) => {
        setConfirmPassword(e.target.value);
    };
    const handleClickShowPassword = () => {
        setShowPassword(!showPassword); // Toggle the visibility of the password
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (professor.password !== confirmPassword) {
            setSnackbarMessage("Passwords do not match!"); // Show the Snackbar
            handleClick();
            return;
        }
        try{
            const result = await authService.registerProfessor(professor);
            if (result.status === 200) {
                console.log("Registered");
                navigate('/login'); // Redirect to login page
            }
        }        
        catch (error) {
            setSnackbarMessage('Please fill all the fields');
            handleClick();
        }

        console.log(professor);
    };

        //snackbar
        const handleClick = () => {
            setOpen(true);
        };
    
        const handleClose = (event, reason) => {
            if (reason === 'clickaway') {
                return;
            }
            setOpen(false);
        };

        const action = (
            <React.Fragment>
                <IconButton size="small" aria-label="close" color="inherit" onClick={handleClose}>
                    <CloseIcon fontSize="small"/>
                </IconButton>
            </React.Fragment>
        );

    return (
        <ThemeProvider theme={lightTheme}>

            <form>
                <div className="fields">
                    <TextField sx={{m: 1, width: '30ch'}} className="fields" name="university" label="University"
                               value={professor.university}
                               onChange={handleChange}/>
                </div>
                <div className="fields">
                    <TextField sx={{m: 1, width: '30ch'}} className="fields" name="name" label="Name"
                               value={professor.name}
                               onChange={handleChange}/>
                </div>
                <div className="fields">
                    <TextField sx={{m: 1, width: '30ch'}} className="fields" name="surname" label="Surname"
                               value={professor.surname}
                               onChange={handleChange}/>
                </div>
                <div className="fields">
                    <TextField sx={{m: 1, width: '30ch'}} className="fields" name="email" label="Email"
                               value={professor.email}
                               onChange={handleChange}/>
                </div>
                <div className="fields">
                    <TextField
                        sx={{m: 1, width: '30ch'}}
                        className="fields"
                        name="password"
                        label="Password"
                        type={showPassword ? 'text' : 'password'} // Change the type based on the visibility state
                        value={professor.password}
                        onChange={handleChange}
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton
                                        aria-label="toggle password visibility"
                                        onClick={handleClickShowPassword}
                                    >
                                        {showPassword ? <VisibilityOff/> : <Visibility/>}
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                </div>
                <div className="fields">
                    <TextField
                        sx={{m: 1, width: '30ch'}}
                        className="fields"
                        name="confirmPassword"
                        label="Confirm Password"
                        type={showPassword ? 'text' : 'password'} // Change the type based on the visibility state
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton
                                        aria-label="toggle password visibility"
                                        onClick={handleClickShowPassword}
                                    >
                                        {showPassword ? <VisibilityOff/> : <Visibility/>}
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                </div>
                <Button onClick={handleSubmit}
                        id="login"
                        variant="contained"
                        style={{marginTop: "50px", textTransform: 'none'}}
                        sx={{m: 1, width: '39ch'}}
                >Register</Button>
                <Snackbar
                        open={open}
                        autoHideDuration={1000}
                        onClose={handleClose}
                        message={snackbarMessage}
                        action={action}
                    />
            </form>
        </ThemeProvider>
    );
};

export default ProfessorRegistration;