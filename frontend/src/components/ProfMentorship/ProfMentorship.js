import React, {useEffect, useState} from 'react';
import programService from '../../services/ProgramService'
import {Card, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import Button from '@mui/material/Button';
import Icon from '@mui/material/Icon';
import ProfNavigation from "../ProfNavigation/ProfNavigation";
import darkTheme from "../../themes/darkTheme";
import {ThemeProvider} from "@mui/material/styles";
import TextField from "@mui/material/TextField";
import authService from "../../services/AuthService";
import {useNavigate} from "react-router-dom"; // Assuming you're using react-bootstrap for UI components

const ProfMentorshipCards = () => {
    const [mentorships, setMentorships] = useState([]);
    const [show, setShow] = useState(false);
    const [email, setEmail] = useState('');
    const navigate = useNavigate();

    function validateRole() {
        const role = authService.validateUser();
        if (role !== 'professor') {
            navigate('/login');
        }
    }

    useEffect(() => {
        validateRole();
        const handleUnauthorized = () => {
            authService.logout();
            navigate('/login');
        };

        window.addEventListener('unauthorized', handleUnauthorized);
        fetchMentorships();

        return () => {
            window.removeEventListener('unauthorized', handleUnauthorized);
        };
    }, []);


    const handleClose = () => {
        setEmail(''); // Clear the form
        setShow(false);
    };
    const handleShow = () => setShow(true);

    const handleSave = async () => {
        try {
            const response = await programService.addMentorship(email);
            if (response)
                if (response.status === 200){
                    let mentorship = [...mentorships, response.data.body];
                    setMentorships(mentorship);
                    console.log(mentorship);
                    console.log('Mentorship added:', mentorships);
                }
            handleClose();
        } catch (error) {
            console.log('Error adding mentorship:', error);
        }
    };

    const fetchMentorships = async () => {
        try {
            const response = await programService.getProfMentorship();
            console.log(response);
            setMentorships(response.data);
        } catch (error) {
            console.log('Error fetching mentorships:', error);
        }
    };

    const handleDelete = async (id) => {
        const response = await programService.deleteMentorship(id);
        if (response.status === 200) {
            setMentorships(mentorships.filter((mentorship) => mentorship.id !== id));
        }
        console.log(`Delete mentorship with id: ${id}`);
    };

    return (
        <div>
            <ThemeProvider theme={darkTheme}>
            <ProfNavigation/>
                <div className='App'>
                    {mentorships.map((mentorship) => (
                        <Card key={mentorship.id} className='program-card profMentor'>
                            <h2>Mentorship student info</h2>
                            <p>Name: <strong>{mentorship.studentName}</strong></p>
                            <p>Email:<strong> {mentorship.studentEmail}</strong></p>
                            <Button onClick={() => handleDelete(mentorship.id)}>
                                <Icon>delete</Icon>
                            </Button>
                        </Card>
                    ))}
                    <Button onClick={handleShow}>
                        <Icon>add</Icon>
                    </Button>
                    <Dialog open={show} onClose={handleClose}>
                        <DialogTitle>Add Mentorship</DialogTitle>
                        <DialogContent>
                            <TextField label="Email" value={email} onChange={(e) => setEmail(e.target.value)} fullWidth
                                       />
                        </DialogContent>
                        <DialogActions>
                            <Button  onClick={handleClose}>
                                <Icon>close</Icon>
                            </Button>
                            <Button  onClick={handleSave}>
                                <Icon>add</Icon>
                            </Button>
                        </DialogActions>
                    </Dialog>


                </div>
            </ThemeProvider>
        </div>
    );
};

export default ProfMentorshipCards;