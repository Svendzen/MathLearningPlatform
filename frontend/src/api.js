import axios from 'axios';
import.meta.env

// Create a pre-configured axios instance for our API
const api = axios.create({
    // The baseURL will be automatically prepended to all requests
    baseURL: 'http://localhost:8000/api/v1',
    // Reasonable timeout to prevent hanging requests
    timeout: 10000,
    // Ensures consistent data handling
    headers: {
        'Content-Type': 'application/json'
    }
});

// Add response interceptor for consistent error handling
api.interceptors.response.use(
    response => response.data,
    error => {
        // Log errors in development
        if (process.env.NODE_ENV === 'development') {
            console.error('API Error:', error.response);
        }
        return Promise.reject(error);
    }
);

export default api;