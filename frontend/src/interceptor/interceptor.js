import axios from 'axios';
import authService from '../services/AuthService';

const httpClient = axios.create();

httpClient.interceptors.request.use(
    async (config) => {
        const token = await authService.getToken();
        config.headers['Authorization'] = `Bearer ${token}`;
        
        // // Add CORS headers
        // config.headers['Access-Control-Allow-Origin'] = 'http://localhost:3000'; 
        // config.headers['Access-Control-Allow-Methods'] = 'GET, POST, PUT, DELETE, OPTIONS'; 
        // config.headers['Access-Control-Allow-Headers'] = '*'; 

        return config;
    },
    (error) => {
        console.error('Error setting Authorization header:', error);
        return Promise.reject(error);
    }
);

export default httpClient;