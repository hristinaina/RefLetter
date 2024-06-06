import { createTheme } from '@mui/material/styles';

const lightTheme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#895061'
    },
    secondary: {
      main: '#2D4159',
    },
    background: {
      default: '#3a0820',
      paper: '#3a0820',
    },
    text: {
      primary: '#000000',
      secondary:'#000000',
    },
  },
});

export default lightTheme;
