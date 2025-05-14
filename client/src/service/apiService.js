import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080',
});

// Automatically include JWT in headers if available
apiClient.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default {
    // Auth
    registerUser(data) {
        try {
            return apiClient.post('/user/register', data);
        } catch (e) {
            console.error(e)
        }
    }, login(data) {
        try {
            return apiClient.post('/user/auth/local', data);
        } catch (e) {
            console.error(e)
        }
    }, loginOrRegisterWithGoogle(idToken) {
        try {
            return apiClient.post('user/auth/google', {idToken});
        } catch (e) {
            console.error(e)
        }
    }
};