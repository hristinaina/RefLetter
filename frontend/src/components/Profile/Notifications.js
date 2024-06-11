import React, { useEffect, useState } from 'react';
import { List, ListItem, ListItemText, ThemeProvider, Typography } from '@mui/material';
import userService from '../../services/UserService';
import lightTheme from '../../themes/darkTheme';
import "./Profile.css"
import {Card} from "@mui/material";

const Notifications = () => {
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        let isMounted = true; // flag to prevent state update if component is unmounted

        const fetchNotifications = async () => {
            try {
                const result = await userService.getNotifications();
                if (result.status === 200 && isMounted) {
                    console.log("Received notifications");
                    setNotifications(result.data); // Update the state with the received data
                }
            } catch (error) {
                if (isMounted) {
                    console.log(error);
                }
            }
        };

        fetchNotifications();

        // Cleanup function
        return () => {
            isMounted = false;
        };
    }, []); // Empty dependency array means this effect runs once after initial render

    return (
        <ThemeProvider theme={lightTheme} className="notification-container">
            <p className='about-you'>
                Don't Miss Out!
            </p>
            {notifications.length < 1 && (
                <p style={{fontSize: "1.4em", textAlign: "center", marginTop: "50px"}}>No data to show! </p>
            )}
            {notifications.length >= 1 && (
            <List className='notification-list'>
                {notifications.map(notification => (
                    <ListItem key={notification.id} className='notification-item'>
                        <Card className='notif'>
                        <ListItemText
                            primary={notification.message + " Check out the program  " + notification.programName} 
                            secondary={`Notified at: ${new Date(notification.notifiedAt).toLocaleString()}`}
                            sx={{
                                '& .MuiListItemText-secondary': {
                                    color: 'var(--background-blue)', // Change to the desired color
                                    paddingTop: "5px",
                                }
                            }}
                        />
                        </Card>
                    </ListItem>
                ))}
            </List>
        )}
        </ThemeProvider>
    );
};

export default Notifications;
