import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#0677A1'
    },
    secondary: {
      main: '#2D4159',
    },
    background: {
      default: '#59253A',
      paper: '#59253A',
    },
    text: {
      primary: '#fff',
      secondary:'#fff',
    },
  },
});

export default theme;
