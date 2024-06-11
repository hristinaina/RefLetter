import React, { useState, useEffect } from 'react';
import './Profile.css';
import authService from '../../services/AuthService';
import darkTheme from '../../themes/darkTheme';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import StudentNavigation from '../StudentNavigation/StudentNavigation';
import { ThemeProvider } from '@emotion/react';
import StudentRegistration from '../Registration/StudentRegistration';
import Notifications from './Notifications';
import Statistics from './Statistics';

const StudentProfile = () => {
  const [selectedOption, setSelectedOption] = useState('PROFILE');
  const [profileImage, setProfileImage] = useState('');

  const [user, setUser] = useState({});

  const [open, setOpen] = React.useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState(''); 

  const handleOptionChange = (option) => {
    setSelectedOption(option);
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
  };

  const action = (
    <React.Fragment>
      <IconButton
        size="small"
        aria-label="close"
        color="inherit"
        onClick={handleClose}
      >
        <CloseIcon fontSize="small" />
      </IconButton>
    </React.Fragment>
  );

  return (
    <ThemeProvider theme={darkTheme}>
      <StudentNavigation />
      <div className="user-profile-container">
        <div className="side-menu">
          <div className='container-image'>
            <img
              id="profile-image"
              src={user['Role'] === 1 ? profileImage : "/images/user.png"}
              alt="User"
            />
            <img id='add-image' src="/images/add.png" alt="Add Image" />
          </div>
          <p style={{color: 'var(--background-red)'}}>pls</p>
          <div
            className={`menu-option ${selectedOption === 'PROFILE' ? 'selected' : ''}`}
            onClick={() => handleOptionChange('PROFILE')}
          >
            DETAILS
          </div>
          <div
            className={`menu-option ${selectedOption === 'NOTIFICATIONS' ? 'selected' : ''}`}
            onClick={() => handleOptionChange('NOTIFICATIONS')}
          >
            NOTIFICATIONS
          </div>
          <div
            className={`menu-option ${selectedOption === 'STATISTICS' ? 'selected' : ''}`}
            onClick={() => handleOptionChange('STATISTICS')}
          >
            STATISTICS
          </div>
        </div>

        <div className="content">
          {selectedOption === 'PROFILE' && (
            <>
              <form className='update-form'> 
                <p className='about-you'>About you</p>
                <StudentRegistration theme={darkTheme} updated={true} />
              </form>
            </>
          )}
          {selectedOption === 'NOTIFICATIONS' && (
            <>
              <form className='update-form'> 
                <Notifications/>
              </form>
            </>
          )}
          {selectedOption === 'STATISTICS' && (
            <>
              <form className='update-form'> 
                <Statistics/>
              </form>
            </>
          )}
        </div>
      </div>
    </ThemeProvider>
  );
};

export default StudentProfile;
