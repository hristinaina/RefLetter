import React, {useEffect, useState} from 'react';
import { useTheme } from '@mui/material/styles';
import Navigation from './Navigation/Navigation';
import darkTheme from '../themes/darkTheme';
import { ThemeProvider } from '@emotion/react';

export function Programs() {
    const theme = useTheme()
    return (
        <ThemeProvider theme={theme}>
        <div className='App'>
            <Navigation></Navigation>

        </div>
        </ThemeProvider>
    );
}