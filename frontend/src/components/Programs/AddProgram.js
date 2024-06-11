import React, {useEffect, useState, useRef} from 'react';
import StudentNavigation from '../StudentNavigation/StudentNavigation';
import darkTheme from '../../themes/darkTheme';
import { ThemeProvider } from '@emotion/react';
import programService from "../../services/ProgramService";
import {Card, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import Icon from "@mui/material/Icon";
import "./Programs.css";
import authService from '../../services/AuthService';
import ProfNavigation from '../ProfNavigation/ProfNavigation';
import { TextField, Button, Chip, Snackbar, Checkbox, FormControlLabel } from '@mui/material';
import IconButton from "@mui/material/IconButton";


const AddProgramDialog = ({ setData, data }) => {

    const [student, setStudent] = useState({
        name: '',
        price: '',
        universityName: '',
        gpa: '',
        researchInterest: [],
        testScores: {},
        researchExperience: []
    });

        // add dialog
        const [show, setShow] = useState(false);
        const priceInputRef = useRef();
        const gpaInputRef = useRef();
        const [interestInput, setInterestInput] = useState('');
        const [experienceInput, setExperienceInput] = useState('');
        const [testNameInput, setTestNameInput] = useState('');
        const [testScoreInput, setTestScoreInput] = useState('');
    
        const handleShow = () => setShow(true);
        const handleCloseAdd = () => {
            setShow(false);
        };

        
    const handleSave = async () => {
        try {
            const response = await programService.addProgram(student);
            if (response.status === 200){
                let programs = [...data, response.data.body];
                setData(programs);
                console.log(programs);
            }
            handleCloseAdd();
        } catch (error) {
            console.log('Error adding program:', error);
        }
    };

    const handleChange = (e) => {
        if (e.target.name === 'gpa') {
            const trimmedValue = e.target.value.trim();
            if (trimmedValue === '' || isNaN(trimmedValue)) {
                setStudent({ ...student, gpa: '' }); // Clear the GPA field
                gpaInputRef.current.value = '';
                console.log(student);
                return;
            }
        }else if (e.target.name === 'price') {
            const trimmedValue = e.target.value.trim();
            if (trimmedValue === '' || isNaN(trimmedValue)) {
                setStudent({ ...student, price: '' }); 
                priceInputRef.current.value = '';
                console.log(student);
                return;
            }
        }
        console.log(student);
        setStudent({ ...student, [e.target.name]: e.target.value });
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

    const handleAddTestScore = () => {
        if (testNameInput && testScoreInput) {
            setStudent({ ...student, testScores: { ...student.testScores, [testNameInput]: testScoreInput } });
            setTestNameInput('');
            setTestScoreInput('');
        }
    }

    const handleDeleteInterest = (interestToDelete) => {
        setStudent({
            ...student,
            researchInterest: student.researchInterest.filter((interest) => interest !== interestToDelete)
        });
    }

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

    const handleDeleteIcon = (deleteHandler) => (
        <IconButton onClick={deleteHandler} style={{ color: 'var(--light-red)' }}>
            <Icon>cancel</Icon>
        </IconButton>
    );
    

    return (
        <ThemeProvider theme={darkTheme}>
            <Button onClick={handleShow} style={{display: 'block', margin: '0 auto'}}>
                <Icon>add</Icon>
            </Button>
            <Dialog open={show} onClose={handleCloseAdd}>
                <DialogTitle>Add Program</DialogTitle>
                <DialogContent>
                <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="name" label="Name"
                                value={student.name}
                                onChange={handleChange} />
                    <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="price" label="Price"
                                type="number" // Set the type to number
                                value={student.price}
                                inputRef={priceInputRef} 
                                onChange={handleChange}
                                onBlur={handleChange} />
                     <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="universityName" label="University Name"
                                value={student.universityName}
                                onChange={handleChange} />
                    <TextField sx={{ m: 1, width: '30ch' }} className="fields" name="gpa" label="GPA"
                                type="number" // Set the type to number
                                value={student.gpa}
                                inputRef={gpaInputRef} // Attach the ref to the GPA TextField
                                onChange={handleChange}
                                onBlur={handleChange} />
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
                    {student.researchExperience.map((experience, index) => (
                            <Chip
                                key={index}
                                label={experience}
                                deleteIcon={handleDeleteIcon(() => handleDeleteExperience(experience))}
                                onDelete={() => handleDeleteExperience(experience)}
                            />
                        ))}
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
                </DialogContent>
                <DialogActions>
                    <Button  onClick={handleCloseAdd}>
                        <Icon>close</Icon>
                    </Button>
                    <Button  onClick={handleSave}>
                        <Icon>add</Icon>
                    </Button>
                </DialogActions>
            </Dialog>
        </ThemeProvider>
    )

}

export default AddProgramDialog;