import { createTheme } from '@mui/material/styles';

const darkTheme = createTheme({
    palette: {
      mode: 'dark',
      primary: {
        main: '#c7e410'
      },
      secondary: {
        main: '#2D4159',
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
  