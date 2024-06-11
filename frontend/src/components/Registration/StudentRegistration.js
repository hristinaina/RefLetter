import React, { useRef, useState, useEffect } from 'react';
import { TextField, Button, Chip, Snackbar, Checkbox, FormControlLabel } from '@mui/material';
import authService from "../../services/AuthService";
import { useNavigate } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import Visibility from "@mui/icons-material/Visibility";
import Icon from "@mui/material/Icon";
import CloseIcon from '@mui/icons-material/Close';
import userService from '../../services/UserService';

const StudentRegistration = ({ theme, updated }) => {
    const navigate = useNavigate();
    const [interestInput, setInterestInput] = useState('');
    const [experienceInput, setExperienceInput] = useState('');
    const gpaInputRef = useRef();
    const [isChecked, setIsChecked] = useState(false);

    const [student, setStudent] = useState({
        name: '',
        surname: '',
        email: '',
        password: '',
        gpa: '',
        location: '',
        requiresFinancialAid: false,
        researchInterest: [],
        testScores: {},
        researchExperience: []
    });

    const [confirmPassword, setConfirmPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [testNameInput, setTestNameInput] = useState('');
    const [testScoreInput, setTestScoreInput] = useState('');
    const [open, setOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');

    useEffect(() => {
        const fetchProfileData = async () => {
            if (updated) {
                try {
                    const currentUser = await authService.getProfileData();
                    console.log(currentUser);
                    setStudent({
                        ...student,
                        id: currentUser.id,
                        name: currentUser.name,
                        surname: currentUser.surname,
                        email: currentUser.email,
                        gpa: currentUser.gpa,
                        location: currentUser.location,
                        requiresFinancialAid: currentUser.requiresFinancialAid,
                        researchInterest: currentUser.researchInterest || [],
                        testScores: currentUser.testScores || {},
                        researchExperience: currentUser.researchExperience || []
                    });
                    setIsChecked(currentUser.requiresFinancialAid);
                } catch (error) {
                    console.error('Error fetching profile data:', error);
                }
            }
        };

        fetchProfileData();
    }, [updated]);

    const handleChange = (e) => {
        if (e.target.name === 'gpa') {
            const trimmedValue = e.target.value.trim();
            if (trimmedValue === '' || isNaN(trimmedValue)) {
                setSnackbarMessage("GPA must be a number!");
                handleClick();
                setStudent({ ...student, gpa: '' }); // Clear the GPA field
                gpaInputRef.current.value = '';
                console.log(student);
                return;
            }
        }
        console.log(student);
        setStudent({ ...student, [e.target.name]: e.target.value });
    };

    const handleAddTestScore = () => {
        if (testNameInput && testScoreInput) {
            setStudent({ ...student, testScores: { ...student.testScores, [testNameInput]: testScoreInput } });
            setTestNameInput('');
            setTestScoreInput('');
        }
    }

    const handleCheckboxChange = (event) => {
        setIsChecked(event.target.checked);
        setStudent({ ...student, requiresFinancialAid: event.target.checked });
    };

    const handleConfirmPasswordChange = (e) => {
        setConfirmPassword(e.target.value);
    };

    const handleClickShowPassword = () => {
        setShowPassword(!showPassword); // Toggle the visibility of the password
    };

    const handleChipChange = (chips) => {
        setStudent({ ...student, researchInterest: chips });
    };

    const handleScoreChange = (e) => {
        setStudent({ ...student, testScores: { ...student.testScores, [e.target.name]: e.target.value } });
    };


    const createStudent= async (e) =>{
        if (student.password !== confirmPassword) {
            setSnackbarMessage("Passwords do not match!"); // Show the Snackbar
            handleClick();
            return;
        }
        try {
            const result = await authService.registerStudent(student);
            if (result.status === 200) {
                console.log("Registered");
                navigate('/login'); // Redirect to login page
            }
        } catch (error) {
            setSnackbarMessage('Please fill all the fields');
            handleClick();
        }
        console.log(student);
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!updated) createStudent(e);
        else{
            try {
                const result = await userService.updateStudent(student);
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

    const handleAddInterest = () => {
        if (interestInput) {
            setStudent({ ...student, researchInterest: [...student.researchInterest, interestInput] });
            setInterestInput('');
        }
    }

    const handleAddExperience = () => {
        if (experienceInput) {
            setStudent({ ...student, researchExperience: [...student.researchExperience, experienceInput] });
            setExperienceInput('');
        }
    }

    const handleDeleteInterest = (interestToDelete) => {
        setStudent({
            ...student,
            researchInterest: student.researchInterest.filter((interest) => interest !== interestToDelete)
        });
    }

    const handleDeleteIcon = (deleteHandler) => (
        <IconButton onClick={deleteHandler} style={{ color: 'var(--light-red)' }}>
            <Icon>cancel</Icon>
        </IconButton>
    );

    const handleDeleteExperience = (experienceToDelete) => {
        setStudent({
            ...student,
            researchExperience: student.researchExperience.filter((experience) => experience !== experienceToDelete)
        });
    }

    const handleDeleteTestScore = (testNameToDelete) => {
        const { [testNameToDelete]: _, ...newTestScores } = student.testScores;
        setStudent({ ...student, testScores: newTestScores });
    }

    // Snackbar
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
                <CloseIcon fontSize="small" />
            </IconButton>
        </React.Fragment>
    );

    return (
        <ThemeProvider theme={theme}>
            <form>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <div className='left-side-reg' style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                        <div className="fields">
                            <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="name" label="Name"
                                value={student.name}
                                onChange={handleChange} />
                        </div>
                        <div className="fields">
                            <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="surname" label="Surname"
                                value={student.surname}
                                onChange={handleChange} />
                        </div>
                        <div className="fields">
                            <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="email" label="Email"
                                value={student.email}
                                onChange={handleChange} />
                        </div>
                        {!updated && (
                            <div className="fields">
                                <TextField
                                    sx={{ m: 1, width: '30ch' }}
                                    className="fields"
                                    name="password"
                                    label="Password"
                                    type={showPassword ? 'text' : 'password'} // Change the type based on the visibility state
                                    value={student.password}
                                    onChange={handleChange}
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position="end">
                                                <IconButton
                                                    aria-label="toggle password visibility"
                                                    onClick={handleClickShowPassword}
                                                >
                                                    {showPassword ? <VisibilityOff /> : <Visibility />}
                                                </IconButton>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            </div>
                        )}
                        {!updated && (
                            <div className="fields">
                                <TextField
                                    sx={{ m: 1, width: '30ch' }}
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
                                                    {showPassword ? <VisibilityOff /> : <Visibility />}
                                                </IconButton>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            </div>
                        )}
                        {updated && (
                            <div className="fields">
                                <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="gpa" label="GPA"
                                    type="number" // Set the type to number
                                    value={student.gpa}
                                    inputRef={gpaInputRef} // Attach the ref to the GPA TextField
                                    onChange={handleChange}
                                    onBlur={handleChange} />
                            </div>
                        )}
                        {updated && (
                            <FormControlLabel
                            control={
                                <Checkbox
                                style={{ marginLeft: '45px'}}
                                    checked={isChecked}
                                    onChange={handleCheckboxChange}
                                    name="checkbox"
                                    color="primary"
                                />
                            }
                            label="Requires financial aid"
                        />
                        )}
                    </div>
                    <div className='right-side-reg' style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                        {!updated && (
                            <div className="fields">
                                <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="gpa" label="GPA"
                                    type="number" // Set the type to number
                                    value={student.gpa}
                                    inputRef={gpaInputRef} // Attach the ref to the GPA TextField
                                    onChange={handleChange}
                                    onBlur={handleChange} />
                            </div>
                        )}
                        <div className="fields">
                            <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="location" label="Location"
                                value={student.location}
                                onChange={handleChange} />
                        </div>
                        <div className="fields" style={{ display: 'flex', justifyContent: 'center', width: '48.5ch', margin: 'auto' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', width: '100%' }}>
                                <TextField
                                    sx={{ m: 1, flexGrow: 1 }}
                                    className="fields"
                                    name="researchInterest"
                                    label="Add Research Interest"
                                    value={interestInput}
                                    onChange={(e) => setInterestInput(e.target.value)}
                                />
                                <Button onClick={handleAddInterest}>
                                    <Icon>add</Icon>
                                </Button>
                            </div>
                        </div>
                        <div className="fields" style={{ display: 'flex', justifyContent: 'center', flexWrap: 'wrap', width: '48.5ch', margin: 'auto' }}>
                            {student.researchInterest.map((interest, index) => (
                                <Chip
                                    key={index}
                                    label={interest}
                                    deleteIcon={handleDeleteIcon(() => handleDeleteInterest(interest))}
                                    onDelete={() => handleDeleteInterest(interest)}
                                />
                            ))}
                        </div>
                        <div className="fields" style={{ display: 'flex', justifyContent: 'center', width: '48.5ch', margin: 'auto' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', width: '100%' }}>
                                <TextField
                                    sx={{ m: 1, flexGrow: 1 }}
                                    className="fields"
                                    name="researchExperience"
                                    label="Add Research Experience"
                                    value={experienceInput}
                                    onChange={(e) => setExperienceInput(e.target.value)}
                                />
                                <Button onClick={handleAddExperience}>
                                    <Icon>add</Icon>
                                </Button>
                            </div>
                        </div>
                        <div className="fields" style={{ display: 'flex', justifyContent: 'center', flexWrap: 'wrap', width: '48.5ch', margin: 'auto' }}>
                            {student.researchExperience.map((experience, index) => (
                                <Chip
                                    key={index}
                                    label={experience}
                                    deleteIcon={handleDeleteIcon(() => handleDeleteExperience(experience))}
                                    onDelete={() => handleDeleteExperience(experience)}
                                />
                            ))}
                        </div>
                        <div className="fields" style={{ display: 'flex', justifyContent: 'center', width: '48.5ch', margin: 'auto' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', width: '100%' }}>
                                <TextField
                                    sx={{ m: 1, flexGrow: 1 }}
                                    className="fields"
                                    name="testName"
                                    label="Add Test Name"
                                    value={testNameInput}
                                    onChange={(e) => setTestNameInput(e.target.value)}
                                />
                                <TextField
                                    sx={{ m: 1, flexGrow: 1 }}
                                    className="fields"
                                    name="testScore"
                                    label="Add Test Score"
                                    value={testScoreInput}
                                    onChange={(e) => setTestScoreInput(e.target.value)}
                                />
                                <Button onClick={handleAddTestScore}>
                                    <Icon>add</Icon>
                                </Button>
                            </div>
                        </div>
                        <div className="fields" style={{ display: 'flex', justifyContent: 'center', flexWrap: 'wrap', width: '48.5ch', margin: 'auto' }}>
                            {Object.entries(student.testScores).map(([testName, testScore], index) => (
                                <Chip
                                    key={index}
                                    label={`${testName}: ${testScore}`}
                                    deleteIcon={handleDeleteIcon(() => handleDeleteTestScore(testName))}
                                    onDelete={() => handleDeleteTestScore(testName)}
                                />
                            ))}
                        </div>
                    </div>
                </div>
                <Button
                    id="login"
                    variant="contained"
                    style={{ marginTop: "50px", textTransform: 'none', width: '35%', marginLeft: 'auto', marginRight: 'auto', display: 'block' }}
                    sx={{ m: 1, width: '39ch' }}
                    onClick={handleSubmit}
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

export default StudentRegistration;
