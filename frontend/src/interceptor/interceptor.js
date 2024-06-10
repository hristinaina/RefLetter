import axios from 'axios';
import authService from '../services/AuthService';

const httpClient = axios.create();

httpClient.interceptors.request.use(
    async (config) => {
        const skipUrls = ['/api/register/student', '/api/register/professor', '/api/login'];

        // Only add the token if the URL is not in the skipUrls array
        if (!skipUrls.includes(config.url)) {
            const token = await authService.getToken();
            config.headers['Authorization'] = `Bearer ${token}`;
        }

        return config;




        // // Add CORS headers
        config.headers['Access-Control-Allow-Origin'] = '*'; // Allow all origins
        config.headers['Access-Control-Allow-Methods'] = 'GET, POST, PUT, DELETE, OPTIONS';
        config.headers['Access-Control-Allow-Headers'] = 'Origin, X-Requested-With, Content-Type, Accept, Authorization';
        config.headers['Access-Control-Allow-Credentials'] = 'true';
        return config;
    },
    (error) => {
        console.error('Error setting Authorization header:', error);
        return Promise.reject(error);
    }
);

export default httpClient;