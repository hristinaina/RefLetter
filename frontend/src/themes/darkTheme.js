import { backdropClasses } from '@mui/material';
import { createTheme } from '@mui/material/styles';

const darkTheme = createTheme({
    palette: {
      mode: 'dark',
      primary: {
        main: '#DAD8D8'
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
    components: {
    MuiButton: {
      styleOverrides: {
        root: {
          '&:hover': {
            backgroundColor: 'var(--background-blue)', // Custom hover color for buttons
          },
        },
      },
    },
    MuiIconButton: {
      styleOverrides: {
        root: {
          '&:hover': {
            color: 'var(--background-blue)', // Custom hover color for icon buttons
          },
        },
      },
    },
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
            color: 'white',
            backgroundColor: 'var(--light-red)', // Keep background color on focus
            borderColor: 'var(--background-blue)',
          }, // Background color for outlined variant
          '&:hover .MuiOutlinedInput-notchedOutline': {
            borderColor: 'var(--background-blue)', // Hover border color
          },
        }
      },
    },

  },
  });
  
  export default darkTheme;
  