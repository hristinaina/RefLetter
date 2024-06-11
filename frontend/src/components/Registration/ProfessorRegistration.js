import React, {useState, useEffect} from 'react';
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
import userService from '../../services/UserService';

const ProfessorRegistration = ({ theme, updated }) => {
    const navigate = useNavigate();
    const [confirmPassword, setConfirmPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [open, setOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [errors, setErrors] = useState({});

    const [professor, setProfessor] = useState({
        university: '',
        name: '',
        surname: '',
        email: '',
        password: ''
    });



    const validatePassword = (password) => {
        // At least 8 characters long, 1 uppercase letter, 1 lowercase letter, 1 number, 1 special character
        const regex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@#$%^&(){}[\]:;<>,.?/~_+\-=|\\]).{8,20}$/;
        return regex.test(password);
    };


    const validateForm = () => {
        let tempErrors = {};
        let missingFields = [];
        if (!professor.university) {
            tempErrors.university = "University is required.";
            missingFields.push('University');
        }
        if (!professor.name) {
            tempErrors.name = "Name is required.";
            missingFields.push('Name');
        }
        if (!professor.surname) {
            tempErrors.surname = "Surname is required.";
            missingFields.push('Surname');
        }
        if (!professor.email) {
            tempErrors.email = "Email is required.";
            missingFields.push('Email');
        }
        if (!professor.password) {
            tempErrors.password = "Password is required.";
            missingFields.push('Password');
        } else if (!validatePassword(professor.password)) {
            tempErrors.password = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character.";
        }
        setErrors(tempErrors);
        return missingFields;
    };

    
    useEffect(() => {
        const fetchProfileData = async () => {
            if (updated) {
                try {
                    const currentUser = await authService.getProfileData();
                    console.log(currentUser);
                    setProfessor({
                        ...professor,
                        id: currentUser.id,
                        name: currentUser.name,
                        surname: currentUser.surname,
                        email: currentUser.email,
                        university: currentUser.university.name,
                    });
                } catch (error) {
                    console.log('Error fetching profile data:', error);
                }
            }
        };

        fetchProfileData();
    }, [updated]);

    const handleChange = (e) => {
        setProfessor({...professor, [e.target.name]: e.target.value});
    };

    const handleConfirmPasswordChange = (e) => {
        setConfirmPassword(e.target.value);
    };
    const handleClickShowPassword = () => {
        setShowPassword(!showPassword); // Toggle the visibility of the password
    };

    const createProfessor = async (e) =>{
        if (professor.password !== confirmPassword) {
            setSnackbarMessage("Passwords do not match!"); // Show the Snackbar
            handleClick();
            return;
        }
        try{
            const result = await authService.registerProfessor(professor);
            if (result)
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
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        const missingFields = validateForm();
        if (missingFields.length > 0) {
            setSnackbarMessage(`Missing fields: ${missingFields.join(', ')}`);
            return;
        }
        if (!updated) createProfessor(e);
        else{
            try {
                const result = await userService.updateProfessor(professor);
                if (result.status === 200) {
                    console.log("updated");
                    setSnackbarMessage("Successfully updated account data");
                    handleClick();
                }
            } catch (error) {
                setSnackbarMessage('Update was not successfull. Please check your data.');
                handleClick();
            }
        }
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
        <ThemeProvider theme={theme}>

            <form id='professor-reg'>
                <div className="fields">
                    <TextField sx={{m: 1, width: '30ch'}} className="fields" name="university" label="University"
                               value={professor.university}
                               onChange={handleChange}
                               error={Boolean(errors.university)}
                               helperText={errors.university}/>
                </div>
                <div className="fields">
                    <TextField sx={{m: 1, width: '30ch'}} className="fields" name="name" label="Name"
                               value={professor.name}
                               onChange={handleChange}
                               error={Boolean(errors.name)}
                               helperText={errors.name}/>
                </div>
                <div className="fields">
                    <TextField sx={{m: 1, width: '30ch'}} className="fields" name="surname" label="Surname"
                               value={professor.surname}
                               onChange={handleChange}
                               error={Boolean(errors.surname)}
                               helperText={errors.surname}/>
                </div>
                <div className="fields">
                    <TextField sx={{m: 1, width: '30ch'}} className="fields" name="email" label="Email"
                               value={professor.email}
                               onChange={handleChange}
                               error={Boolean(errors.email)}
                               helperText={errors.email}/>
                </div>
                { !updated && (<div className="fields">
                    <TextField
                        sx={{m: 1, width: '30ch'}}
                        className="fields"
                        name="password"
                        label="Password"
                        type={showPassword ? 'text' : 'password'} // Change the type based on the visibility state
                        value={professor.password}
                        onChange={handleChange}
                        error={Boolean(errors.password)}
                        helperText={errors.password}
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
                )}
                {!updated && (<div className="fields">
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
                </div> )}
                <Button onClick={handleSubmit}
                        id="login"
                        variant="contained"
                        style={{marginTop: "50px", textTransform: 'none', width: '80%', marginLeft: 'auto', marginRight: 'auto', display: 'block'}}
                    
                >{updated ? 'Update' : 'Register'}</Button>
                <Snackbar
                        open={open}
                        autoHideDuration={4000}
                        onClose={handleClose}
                        message={snackbarMessage}
                        action={action}
                    />
            </form>
        </ThemeProvider>
    );
};

export default ProfessorRegistration;