import React, {useEffect, useState} from "react";
import programService from "../../services/ProgramService";
import {ThemeProvider} from "@emotion/react";
import darkTheme from "../../themes/darkTheme";
import Navigation from "../Navigation/Navigation";
import Button from "@mui/material/Button";
import Icon from "@mui/material/Icon";
import {Card, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import TextField from "@mui/material/TextField";
import styles from './Mentorship.module.css';

export const Mentorship = () => {
    const [data, setData] = useState([]);
    const [selectedProgramCardId, setSelectedProgramCardId] = useState(null);
    const [selectedProfCardId, setSelectedProfCardId] = useState(null);
    const [selectedProgram, setSelectedProgram] = useState(null);
    const [professors, setProfessors] = useState([]);

    useEffect(() => {
        const fetchProfessors = async () => {
            const result = await programService.getAllProfs();
            if (result.status === 200) {
                setProfessors(result.data);
            }
        };

        fetchProfessors();
    }, []);


    const handleProgramCardClick = async (programID) => {
        setSelectedProgramCardId(programID);
        const result = await programService.getFinancialAids(programID);
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

    const handleProfCardClick = async (id) => {
        setSelectedProfCardId(id);
        setSelectedProgramCardId(null); // Reset selected program card ID
        setSelectedProgram(null); // Reset selected program
        const result = await programService.getMentorshipPrograms(id);
        if (result.status === 200) {
            setData(result.data);
        }
    }

    return (
        <ThemeProvider theme={darkTheme}>
            <div className='App'>
                <Navigation></Navigation>
                <div className={styles['left-side']}>
                    {professors.map((professor) => (
                        <Card key={professor.id}
                              className={`program-card ${professor.id === selectedProfCardId ? 'selected' : ''}`}
                              onClick={() => handleProfCardClick(professor.id)}>
                            <h2>{professor.name} {professor.surname}</h2>
                            <p>University: {professor.universityName}</p>
                            <p>Location: {professor.location}</p>
                        </Card>
                    ))}
                </div>
                <div className={styles['middle-side']}>
                    {dataToShow.map((item) => (
                        <Card key={item.id}
                              className={`program-card ${item.id === selectedProgramCardId ? 'selected' : ''}`}
                              onClick={() => handleProgramCardClick(item.id)}>
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
                    <div className={styles['right-side']}>

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