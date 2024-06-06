import React, {useEffect, useState} from 'react';
import Navigation from './Navigation/Navigation';
import darkTheme from '../themes/darkTheme';
import { ThemeProvider } from '@emotion/react';

export function Programs() {
    return (
        <ThemeProvider theme={darkTheme}>
        <div className='App'>
            <Navigation></Navigation>

        </div>
        </ThemeProvider>
    );
}