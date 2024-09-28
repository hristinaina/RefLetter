import React, {useEffect, useState} from "react";
import programService from "../../services/ProgramService";
import {ThemeProvider} from "@emotion/react";
import darkTheme from "../../themes/darkTheme";
import StudentNavigation from "../StudentNavigation/StudentNavigation";
import Button from "@mui/material/Button";
import Icon from "@mui/material/Icon";
import {Card, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import TextField from "@mui/material/TextField";
import styles from './Mentorship.module.css';
import authService from "../../services/AuthService";
import {useNavigate} from "react-router-dom";
import moment from "moment";

export const Mentorship = () => {
    const [data, setData] = useState([]);
    const [selectedProgramCardId, setSelectedProgramCardId] = useState(null);
    const [selectedProfCardId, setSelectedProfCardId] = useState(null);
    const [selectedProgram, setSelectedProgram] = useState(null);
    const [professors, setProfessors] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const role = authService.validateUser();
        if (role !== 'student') {
            navigate('/login');
        }
        const fetchProfessors = async () => {
            const result = await programService.getAllProfs();
            if (result)
                if (result.status === 200) {
                    setProfessors(result.data);
                }
        };

        const handleUnauthorized = () => {
            authService.logout();
            navigate('/login');
        };

        window.addEventListener('unauthorized', handleUnauthorized);

        fetchProfessors();

    }, []);


    const handleProgramCardClick = async (programID) => {
        setSelectedProgramCardId(programID);
        const result = await programService.getFinancialAids(programID);
        if (result.status === 200) {
            setSelectedProgram(result.data);
        }
    }

    const itemsPerPage = 7; // Change this to the number of items you want per page
    const [currentPage, setCurrentPage] = useState(0);

    const handleNext = () => {
        setCurrentPage((prevPageNumber) => prevPageNumber + 1);
    };

    const handlePrevious = () => {
        setCurrentPage((prevPageNumber) => prevPageNumber - 1);
    };

    const dataToShow = data.slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage);

    const handleProfCardClick = async (id) => {
        setSelectedProfCardId(id);
        setSelectedProgramCardId(null); // Reset selected program card ID
        setSelectedProgram(null); // Reset selected program
        const result = await programService.getMentorshipPrograms(id);
        if (result.status === 200) {
            setData(result.data);
        }
    }

    function formatDate(dateString) {
        return moment(dateString).format('MMMM Do, YYYY');
    }

    return (
        <ThemeProvider theme={darkTheme}>
            <StudentNavigation></StudentNavigation>
            <div className='App'>
                <div className={styles['left-side']}>
                    {professors.map((professor) => (
                        <Card key={professor.id}
                              className={`program-card ${professor.id === selectedProfCardId ? 'selected' : ''}`}
                              onClick={() => handleProfCardClick(professor.id)}>
                            <h2>{professor.name} {professor.surname}</h2>
                            <Icon>work</Icon>
                            <span> {professor.universityName}</span>
                            <p></p>
                            <Icon>location_on</Icon>
                            <span>{professor.location}</span>
                            <p></p>
                        </Card>
                    ))}
                </div>
                <div className={styles['middle-side']}>
                    {dataToShow.map((item) => (
                        <Card key={item.id}
                        className={`program-card ${item.id === selectedProgramCardId ? 'selected' : ''}`}
                        onClick={() => handleProgramCardClick(item.id)}>
                        <div className="card-header">
                            <h2 className="program-name">{item.name}</h2> {/* Program name at the top */}
                        </div>
                            <div className="requirement-list">
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
                    <Button onClick={handlePrevious} disabled={currentPage === 0}>
                        <Icon>chevron_left</Icon>
                    </Button>
                    <Button onClick={handleNext} disabled={(currentPage + 1) * itemsPerPage >= data.length}>
                        <Icon>chevron_right</Icon>
                    </Button>
                </div>
                {selectedProgram && (
                    <div className={styles['right-side']}>
                        <p><strong><Icon>person</Icon> Professor:</strong> {selectedProgram.professorName}</p>
                        <p><strong><Icon>groups</Icon> Number of Students:</strong> {selectedProgram.numberOfStudents}</p>
                        <p><strong><Icon>people_alt</Icon> Student Per Staff:</strong> {selectedProgram.studentPerStaff}</p>
                        <p><strong><Icon>public</Icon> International Student Percent:</strong> {selectedProgram.internationalStudentPercent}</p>
                        <p><strong><Icon>star</Icon> Rank:</strong> {selectedProgram.rank}</p>
                        <p><strong><Icon>score</Icon> Overall Score:</strong> {selectedProgram.overallScore}</p>
                        <p><strong><Icon>trending_up</Icon> Research Score:</strong> {selectedProgram.researchScore}</p>
                        <p><strong><Icon>trending_up</Icon> Citation Score:</strong> {selectedProgram.citationScore}</p>
                        {selectedProgram.financialAids.map((aid, index) => (
                            <Card key={index} className={"financial-aid-card"}>
                                <h3>{aid.type}</h3>
                                
                                <div>
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
                            </Card>
                        ))}
                    </div>
                )}
            </div>
        </ThemeProvider>
    );
}