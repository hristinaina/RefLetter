import {ThemeProvider} from "@emotion/react";
import darkTheme from "../../themes/darkTheme";
import Navigation from "../Navigation/Navigation";
import React, {useEffect, useState} from "react";
import { Card } from '@mui/material';
import Button from '@mui/material/Button';
import Icon from '@mui/material/Icon';
import programService from "../../services/ProgramService";
import './Recommendations.css';

export function Recommendation() {
    const [data, setData] = useState([]);
    const [selectedCardId, setSelectedCardId] = useState(null);
    const [selectedProgram, setSelectedProgram] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            const result = await programService.getRecommendations();
            if (result.status === 200) {
                console.log("DATA received");
                // console.log(result.data);
                setData(result.data);
            }
        };

        fetchData();
    }, []);

    const handleCardClick = async (programID) => {
        setSelectedCardId(programID);
        const result = await programService.getFinancialAids(programID);
        if (result.status === 200) {
            setSelectedProgram(result.data);
        }
    }

    const itemsPerPage = 1; // Change this to the number of items you want per page
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
            <div className='App'>
                <Navigation></Navigation>
                <div style={{width: '50%', float: 'left'}}>
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
                    <div style={{width: '50%', float: 'right'}}>

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