import React, {useEffect, useState} from 'react';
import { useTheme } from '@mui/material/styles';
import Navigation from './Navigation/Navigation';

export function Programs() {
    const theme = useTheme()
    return (
        <div className='App'>
            <Navigation></Navigation>

        </div>

    );
}