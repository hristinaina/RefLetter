import React, {useEffect, useState} from 'react';
import StudentNavigation from '../StudentNavigation/StudentNavigation';
import darkTheme from '../../themes/darkTheme';
import {ThemeProvider} from '@emotion/react';
import programService from "../../services/ProgramService";
import {Card, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import Icon from "@mui/material/Icon";
import "./Programs.css";
import {useNavigate} from "react-router-dom";
import authService from '../../services/AuthService';
import ProfNavigation from '../ProfNavigation/ProfNavigation';
import AddProgramDialog from './AddProgram';
import { TextField, Button, Chip, Snackbar, Checkbox, FormControlLabel } from '@mui/material';
import IconButton from "@mui/material/IconButton";
import CloseIcon from '@mui/icons-material/Close';


export function Programs() {
    const [data, setData] = useState([]);
    const [selectedCardId, setSelectedCardId] = useState(null);
    const [selectedProgram, setSelectedProgram] = useState(null);
    const navigate = useNavigate();
    const [role, setRole] = useState(0);

    //snackbar
    const [openSB, setOpenSB] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');


    function validateRole() {
        const role = authService.validateUser();
        console.log('PROGRAMS ROLE:' + role);
        if (role !== 'student' && role !== 'professor') {
            navigate('/login');
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            validateRole();
            const user = await authService.validateUser();

            try{

                if (user === "professor") {
                    setRole(1);
                    const result = await programService.getByProfessor();
                    if (result) {
                        if (result.status === 200) {
                            setData(result.data);
                        }
                    }
                }
                else{
                    const result = await programService.getAll();
                    if (result)
                    if (result.status === 200) {
                        setData(result.data);
                    }
                }
            } catch(e){
                console.log('no data to show');
            }

            console.log(role);
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
        if(result)
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

    const handleDelete = async (id) => {
        const response = await programService.deleteProgram(id);
        if (response)
        if (response.status === 200) {
            setData(data.filter((item) => item.id !== id));
            setSnackbarMessage('Successfully deleted program!');
            handleClickSB();
        }
        console.log(`Delete program with id: ${id}`);
    };

    
    const handleDeleteAid = async (id) => {
        try {
            const response = await programService.deleteAid(id, selectedCardId);
    
            if (response && response.status === 200) {
                const updatedFinancialAids = selectedProgram.financialAids.filter(aid => aid.id !== id);
    
                const updatedSelectedProgram = {
                    ...selectedProgram,
                    financialAids: updatedFinancialAids
                };
    
                const updatedData = data.map(program => {
                    if (program.id === selectedCardId) {
                        return {
                            ...program,
                            financialAids: updatedFinancialAids
                        };
                    } else {
                        return program;
                    }
                });
    
                setSelectedProgram(updatedSelectedProgram);
                setData(updatedData);
    
                setSnackbarMessage('Successfully deleted financial aid!');
                handleClickSB();
            }
        } catch (error) {
            console.error('Error deleting financial aid:', error);
            setSnackbarMessage('An error occurred while deleting financial aid!');
            handleClickSB();
        }
    };
    
    
        // Snackbar
        const handleClickSB = () => {
            setOpenSB(true);
        };
    
        const handleCloseSB = (event, reason) => {
            if (reason === 'clickaway') {
                return;
            }
            setOpenSB(false);
        };
    
        const action = (
            <React.Fragment>
                <IconButton size="small" aria-label="close" color="inherit" onClick={handleCloseSB}>
                    <CloseIcon fontSize="small" />
                </IconButton>
            </React.Fragment>
        );
    

    const dataToShow = data.slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage);

    return (
        <ThemeProvider theme={darkTheme}>
            {role === 0 && (<StudentNavigation></StudentNavigation>)}
            {role === 1 && (<ProfNavigation></ProfNavigation>)}
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
                            {role == 1 && (
                            <Button onClick={() => handleDelete(item.id)}>
                                <Icon>delete</Icon>
                            </Button>
                            )}
                        </Card>
                    ))}
                    {role == 1 && (
                        <AddProgramDialog setData={setData} data={data}/>
                    )}


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
                                {role == 1 && (
                                    <Button onClick={() => handleDeleteAid(aid.id)}>
                                        <Icon>delete</Icon>
                                    </Button>
                                    )}
                            </Card>
                            
                        ))}
                    </div>
                )}
            </div>
            <Snackbar
                    open={openSB}
                    autoHideDuration={4000}
                    onClose={handleCloseSB}
                    message={snackbarMessage}
                    action={action}
                />
        </ThemeProvider>
    );
}