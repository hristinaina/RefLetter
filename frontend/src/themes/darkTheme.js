import { createTheme } from '@mui/material/styles';

const darkTheme = createTheme({
    palette: {
      mode: 'dark',
      primary: {
        main: '#3a746e'
      },
      secondary: {
        main: '#895061',
      },
      background: {
        default: '#59253A',
        paper: '#59253A',
      },
      text: {
        primary: '#ffffff',
        secondary:'#ffffff',
      },
    },
  });
  
  export default darkTheme;
  