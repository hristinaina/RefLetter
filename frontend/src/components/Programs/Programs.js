import React, {useEffect, useState} from 'react';
import StudentNavigation from '../StudentNavigation/StudentNavigation';
import darkTheme from '../../themes/darkTheme';
import {ThemeProvider} from '@emotion/react';
import programService from "../../services/ProgramService";
import {Card, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import Icon from "@mui/material/Icon";
import "./Programs.css";
import "./Details.css";
import "./FinancialAids.css";
import {useNavigate} from "react-router-dom";
import authService from '../../services/AuthService';
import ProfNavigation from '../ProfNavigation/ProfNavigation';
import AddProgramDialog from './AddProgram';
import { TextField, Button, Chip, Snackbar, Checkbox, FormControlLabel } from '@mui/material';
import IconButton from "@mui/material/IconButton";
import CloseIcon from '@mui/icons-material/Close';
import AddFinancialAid from './AddFinancialAid';
import moment from 'moment';


export function Programs() {
    const [data, setData] = useState([]);
    const [selectedCardId, setSelectedCardId] = useState(null);
    const [selectedProgram, setSelectedProgram] = useState(null);
    const navigate = useNavigate();
    const [role, setRole] = useState(0);
    const [criteria, setCriteria] = useState(false);

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
        console.log(result);
        if (result)
            if (result.status === 200) {
                setSelectedProgram(result.data);
            }

        if (role === 1) return; 
        const criteria = await programService.checkCriteria(programID);
        if (criteria && criteria.status === 200)
            if (criteria.data.length != 0) {
                setCriteria(true);
            }
            else setCriteria(false);
    }

    const itemsPerPage = 7; // Change this to the number of items you want per page
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
    
        function formatDate(dateString) {
            return moment(dateString).format('MMMM Do, YYYY');
        }

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
                            {role == 1 && (
                            <Button onClick={(e) => { e.stopPropagation(); handleDelete(item.id); }} className="delete-button">
                                <Icon>delete</Icon>
                            </Button>
                        )}
                        <div className="card-header">
                            <h2 className="program-name">{item.name}</h2> {/* Program name at the top */}
                        </div>
                            <div className="requirement-list">
                                <Icon className='reqIcon'>assignment</Icon>
                                <div className="requirement-attribute">
                                    <p><strong>GPA:</strong> {item.requirement?.gpa || 'Not specified'}</p>
                                </div>
                                <div className="requirement-attribute">
                                    <p><strong>Research Experience:</strong> {item.requirement?.researchExperience?.join(', ') || 'None'}</p>
                                </div>
                                <div className="requirement-attribute">
                                    <p><strong>Research Interests:</strong> {item.requirement?.researchInterest?.join(', ') || 'None'}</p>
                                </div>
                                <div className="requirement-attribute">
                                    <p><strong>Test Scores:</strong> {item.requirement?.testScores ? Object.entries(item.requirement.testScores).map(([test, score]) => `${test}: ${score}`).join(', ') : 'None'}</p>
                                </div>
                        </div>
                        <div className="card-footer">
                            <div className="location-university">
                                <Icon>location_on</Icon>
                                <span>{item.location}</span>
                                <span className="university-name"> | {item.universityName}</span>
                            </div>
                            <div className="price">
                                <Icon>attach_money</Icon>
                                <span>{item.price}</span>
                            </div>
                        </div>
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
                       {role === 0 && (
                            <div className={`status ${criteria ? 'satisfied' : 'not-satisfied'}`}>
                                <Icon>{criteria ? 'check_circle' : 'cancel'}</Icon>
                                <span>{criteria ? 'Requirements are satisfied!' : 'Requirements are not satisfied!'}</span>
                            </div>
                        )}
                        <div className="program-details-container">
                            <div className="left-column">
                                <p><strong><Icon>person</Icon> Professor:</strong> {selectedProgram.professorName}</p>
                                <p><strong><Icon>groups</Icon> Number of Students:</strong> {selectedProgram.numberOfStudents}</p>
                                <p><strong><Icon>people_alt</Icon> Student Per Staff:</strong> {selectedProgram.studentPerStaff}</p>
                                <p><strong><Icon>public</Icon> International Student Percent:</strong> {selectedProgram.internationalStudentPercent}</p>
                            </div>
                            <div className="right-column">
                                <p><strong><Icon>star</Icon> Rank:</strong> {selectedProgram.rank}</p>
                                <p><strong><Icon>score</Icon> Overall Score:</strong> {selectedProgram.overallScore}</p>
                                <p><strong><Icon>trending_up</Icon> Research Score:</strong> {selectedProgram.researchScore}</p>
                                <p><strong><Icon>trending_up</Icon> Citation Score:</strong> {selectedProgram.citationScore}</p>
                            </div>
                        </div>
                        {role == 1 && (
                            <AddFinancialAid setSelectedProgram={setSelectedProgram} selectedProgram={selectedProgram} selectedProgramId={selectedCardId}/>
                        )}
                        {selectedProgram.financialAids.map((aid, index) => (
                            <Card key={index} className={"financial-aid-card"}>
                                <h3>{aid.type}</h3>
                                
                                <div className="aid-requirement-list">
                                    <div className="requirement-attribute">
                                        <p><strong>GPA:</strong> {aid.requirement?.gpa || 'Not specified'}</p>
                                    </div>
                                    <div className="requirement-attribute">
                                        <p><strong>Research Experience:</strong> {aid.requirement?.researchExperience?.join(', ') || 'None'}</p>
                                    </div>
                                    <div className="requirement-attribute">
                                        <p><strong>Research Interests:</strong> {aid.requirement?.researchInterest?.join(', ') || 'None'}</p>
                                    </div>
                                    <div className="requirement-attribute">
                                        <p><strong>Test Scores:</strong> {aid.requirement?.testScores ? Object.entries(aid.requirement.testScores).map(([test, score]) => `${test}: ${score}`).join(', ') : 'None'}</p>
                                    </div>
                                </div>
                                <div className="card-footer">
                                <p><Icon>calendar_today</Icon> {formatDate(aid.deadline)}</p>
                                    <div className="price">
                                        <Icon>attach_money</Icon>
                                        <span>{aid.amount}</span>
                                    </div>
                                </div>
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