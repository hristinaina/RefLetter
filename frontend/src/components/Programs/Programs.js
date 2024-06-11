import React, {useEffect, useState} from 'react';
import StudentNavigation from '../StudentNavigation/StudentNavigation';
import darkTheme from '../../themes/darkTheme';
import {ThemeProvider} from '@emotion/react';
import programService from "../../services/ProgramService";
import {Card, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import Button from "@mui/material/Button";
import Icon from "@mui/material/Icon";
import TextField from "@mui/material/TextField";
import "./Programs.css";
import {useNavigate} from "react-router-dom";
import authService from "../../services/AuthService";

export function Programs() {
    const [data, setData] = useState([]);
    const [selectedCardId, setSelectedCardId] = useState(null);
    const [selectedProgram, setSelectedProgram] = useState(null);
    const navigate = useNavigate();

    function validateRole() {
        const role = authService.validateUser();
        console.log('PROGRAMS ROLE:' + role);
        if (role !== 'student' && role !== 'professor') {
            navigate('/login');
        }
    }

    useEffect(() => {
        validateRole();
        const fetchData = async () => {
            const result = await programService.getAll();
            if (result)
            if (result.status === 200) {
                setData(result.data);
            }
        };
        const handleUnauthorized = () => {
            authService.logout();
            navigate('/login');
        };

        window.addEventListener('unauthorized', handleUnauthorized);

        fetchData();
    }, []);

    const [open, setOpen] = useState(false);
    const [rank, setRank] = useState('');
    const [location, setLocation] = useState('');
    const [researchScore, setResearchScore] = useState('');
    const [citationScore, setCitationScore] = useState('');

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setRank('');
        setLocation('');
        setResearchScore('');
        setCitationScore('');
    };

    const handleSubmit = async () => {
        const result = await programService.postProgramFilter(rank, location, researchScore, citationScore);
        if (result.status === 200) {
            setData(result.data);
        }
        handleClose();
    };

    const handleCardClick = async (programID) => {
        setSelectedCardId(programID);
        const result = await programService.getFinancialAids(programID);
        if (result)
            if (result.status === 200) {
                setSelectedProgram(result.data);
            }
    }

    const itemsPerPage = 2; // Change this to the number of items you want per page
    const [currentPage, setCurrentPage] = useState(0);

    const handleNext = () => {
        setCurrentPage((prevPageNumber) => prevPageNumber + 1);
    };

    const handlePrevious = () => {
        setCurrentPage((prevPageNumber) => prevPageNumber - 1);
    };

    const dataToShow = data.slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage);

    return (
        <ThemeProvider theme={darkTheme}>
            <StudentNavigation></StudentNavigation>
            <div className='programs-container'>
                <div className='left-side'>
                    <Button onClick={handleOpen} className="filter-button">
                        <Icon>filter_list</Icon>
                        Filter
                    </Button>
                    <Dialog open={open} onClose={handleClose}>
                        <DialogTitle>Filter Programs</DialogTitle>
                        <DialogContent>
                            <TextField label="Rank" value={rank} onChange={(e) => setRank(e.target.value)} fullWidth/>
                            <TextField label="Location" value={location} onChange={(e) => setLocation(e.target.value)}
                                       fullWidth/>
                            <TextField label="Research Score" value={researchScore}
                                       onChange={(e) => setResearchScore(e.target.value)} fullWidth/>
                            <TextField label="Citation Score" value={citationScore}
                                       onChange={(e) => setCitationScore(e.target.value)} fullWidth/>
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={handleClose} style={{color: "white"}}>Cancel</Button>
                            <Button onClick={handleSubmit} style={{color: "white"}}>Submit</Button>
                        </DialogActions>
                    </Dialog>
                    {dataToShow.map((item) => (
                        <Card key={item.id}
                              className={`program-card ${item.id === selectedCardId ? 'selected' : ''}`}
                              onClick={() => handleCardClick(item.id)}>
                            <h2>{item.name}</h2>
                            <p>University: {item.universityName}</p>
                            <p>Requirement: {item.requirementName}</p>
                            <p>Location: {item.location}</p>
                            <p>Price: {item.price}</p>
                        </Card>
                    ))}
                    <Button onClick={handlePrevious} disabled={currentPage === 0}>
                        <Icon>chevron_left</Icon>
                    </Button>
                    <Button onClick={handleNext} disabled={(currentPage + 1) * itemsPerPage >= data.length}>
                        <Icon>chevron_right</Icon>
                    </Button>
                </div>
                {selectedProgram && (
                    <div className='right-side'>

                        <p>Professor: {selectedProgram.professorName}</p>
                        <p>Rank: {selectedProgram.rank}</p>
                        <p>Number of Students: {selectedProgram.numberOfStudents}</p>
                        <p>Student Per Staff: {selectedProgram.studentPerStaff}</p>
                        <p>International Student Percent: {selectedProgram.internationalStudentPercent}</p>
                        <p>Overall Score: {selectedProgram.overallScore}</p>
                        <p>Research Score: {selectedProgram.researchScore}</p>
                        <p>Citation Score: {selectedProgram.citationScore}</p>
                        {selectedProgram.financialAids.map((aid, index) => (
                            <Card key={index} className={"financial-aid-card"}>
                                <p>Type: {aid.type}</p>
                                <p>Amount: {aid.amount}</p>
                                <p>Requirement: {aid.requirement.name}</p>
                                <p>Deadline: {aid.deadline}</p>
                            </Card>
                        ))}
                    </div>
                )}
            </div>
        </ThemeProvider>
    );
}