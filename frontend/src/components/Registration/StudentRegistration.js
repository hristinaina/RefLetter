import React, {useState} from 'react';
import {TextField, Button, Chip} from '@mui/material';
// import ChipInput from 'material-ui-chip-input';
import authService from "../../services/AuthService";
import {useNavigate} from "react-router-dom";
import darkTheme from "../../themes/darkTheme";
import {ThemeProvider} from "@mui/material/styles";

const StudentRegistration = () => {

    const navigate = useNavigate();

    const [student, setStudent] = useState({
        name: '',
        surname: '',
        email: '',
        password: '',
        gpa: '',
        location: '',
        researchInterest: [],
        testScores: {},
        researchExperience: []
    });

    const [confirmPassword, setConfirmPassword] = useState('');

    const handleChange = (e) => {
        setStudent({...student, [e.target.name]: e.target.value});
    };

    const handleConfirmPasswordChange = (e) => {
        setConfirmPassword(e.target.value);
    };

    const handleChipChange = (chips) => {
        setStudent({...student, researchInterest: chips});
    };

    const handleScoreChange = (e) => {
        setStudent({...student, testScores: {...student.testScores, [e.target.name]: e.target.value}});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const result = await authService.registerProfessor(student);
        if (result.status === 200) {
            console.log("Registered");
            navigate('/login'); // Redirect to login page
        }
        console.log(student);
    };

    return (
        <ThemeProvider theme={darkTheme}>
            <div className='App'>
                <form onSubmit={handleSubmit}>
                    <TextField name="name" label="Name" value={student.name} onChange={handleChange}/>
                    <TextField name="surname" label="Surname" value={student.surname} onChange={handleChange}/>
                    <TextField name="email" label="Email" value={student.email} onChange={handleChange}/>
                    <TextField name="password" label="Password" type="password" value={student.password}
                               onChange={handleChange}/>
                    <TextField name="confirmPassword" label="Confirm Password" type="password" value={confirmPassword}
                               onChange={handleConfirmPasswordChange}/>
                    <TextField name="gpa" label="GPA" value={student.gpa} onChange={handleChange}/>
                    <TextField name="location" label="Location" value={student.location} onChange={handleChange}/>
                    <Chip
                        name="researchInterest"
                        label="Research Interests"
                        value={student.researchInterest}
                        onAdd={(chip) => handleChipChange([...student.researchInterest, chip])}
                        onDelete={(chip, index) => handleChipChange(student.researchInterest.filter((_, i) => i !== index))}
                    />
                    <Button type="submit">Register</Button>
                </form>
            </div>
        </ThemeProvider>
    );
};

export default StudentRegistration;