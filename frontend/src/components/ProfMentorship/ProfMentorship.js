import React, {useEffect, useState} from 'react';
import programService from '../../services/ProgramService'
import {Card, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import Button from '@mui/material/Button';
import Icon from '@mui/material/Icon';
import ProfNavigation from "../ProfNavigation/ProfNavigation";
import darkTheme from "../../themes/darkTheme";
import {ThemeProvider} from "@mui/material/styles";
import TextField from "@mui/material/TextField"; // Assuming you're using react-bootstrap for UI components

const ProfMentorshipCards = () => {
    const [mentorships, setMentorships] = useState([]);
    const [show, setShow] = useState(false);
    const [email, setEmail] = useState('');

    useEffect(() => {
        fetchMentorships();
    }, []);


    const handleClose = () => {
        setEmail(''); // Clear the form
        setShow(false);
    };
    const handleShow = () => setShow(true);

    const handleSave = async () => {
        try {
            const response = await programService.addMentorship(email);
            if (response.status === 200){
                let mentorship = [...mentorships, response.data.body];
                setMentorships(mentorship);
                console.log(mentorship);
                console.log('Mentorship added:', mentorships);
            }
            handleClose();
        } catch (error) {
            console.error('Error adding mentorship:', error);
        }
    };

    const fetchMentorships = async () => {
        try {
            const response = await programService.getProfMentorship();
            console.log(response);
            setMentorships(response.data);
        } catch (error) {
            console.error('Error fetching mentorships:', error);
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
                <div className='App'>
                    <ProfNavigation/>
                    {mentorships.map((mentorship) => (
                        <Card key={mentorship.id}>
                            <h2>Student: {mentorship.studentName}</h2>
                            <p>{mentorship.studentEmail}</p>
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