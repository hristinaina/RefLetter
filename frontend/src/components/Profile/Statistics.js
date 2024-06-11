import React, { useEffect, useState } from 'react';
import { List, ListItem, ListItemText, ThemeProvider, Typography } from '@mui/material';
import userService from '../../services/UserService';
import darkTheme from '../../themes/darkTheme';
import "./Profile.css"
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';


const Statistics = () => {
    const [interests, setInterests] = useState([]);

    useEffect(() => {
        let isMounted = true; 

        const fetchInterest = async () => {
            try {
                const result = await userService.getStatistics();
                if (result.status === 200 && isMounted) {
                    console.log("Received statistics");
                    setInterests(result.data); 
                }
            } catch (error) {
                if (isMounted) {
                    console.log(error);
                }
            }
        };

        fetchInterest();

        // Cleanup function
        return () => {
            isMounted = false;
        };
    }, []); 

    const isEmpty = (obj) => {
        return Object.keys(obj).length === 0;
    };

    if (isEmpty(interests)) {
        return (
            <ThemeProvider theme={darkTheme}>
                <p className='about-you'>See your most frequent interests!</p>
                <p style={{fontSize: "1.4em", textAlign: "center", marginTop: "50px"}}>No data to show! </p>
            </ThemeProvider>
        );
    }else return (
        <ThemeProvider theme={darkTheme}>
            <p className='about-you'>See your most frequent interests!</p>
            <p>We've noticed some shifts in your interests recently, so we've put together a snapshot of your activity to provide insights into your most frequent areas of focus. We hope you find this helpful and insightful!</p>  
            <PieChart data={interests} />
        </ThemeProvider>
    );
};


ChartJS.register(ArcElement, Tooltip, Legend);

const PieChart = ({ data }) => {
    
    const getRandomColor = () => {
        // Generate random RGB values in the range of 100-255 for pastel colors
        const r = Math.floor(Math.random() * 155) + 100;
        const g = Math.floor(Math.random() * 155) + 100;
        const b = Math.floor(Math.random() * 155) + 100;
        
        // Construct the RGBA color with 50% opacity
        return `rgba(${r},${g},${b},0.3)`;
    };

    // Generate random colors for each dataset entry
    const backgroundColors = Object.values(data).map(() => getRandomColor());

    const chartData = {
        labels: Object.keys(data),
        datasets: [
            {
                label: '# of occurrences',
                data: Object.values(data),
                backgroundColor: backgroundColors,
            }
        ]
    };

    // Custom style for the container div
    const containerStyle = {
        width: '400px', 
        height: '400px', 
        marginLeft: 'auto',
        marginRight: 'auto',
    };

    return (
        <div style={containerStyle}>
            <Pie data={chartData} />
        </div>
    );
};

export default Statistics;
