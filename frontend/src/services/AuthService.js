import httpClient from "../interceptor/interceptor";
import { jwtDecode } from 'jwt-decode';

class AuthService {
  
    async loginUser(email, password) {
      try {
        const response = await httpClient.post('http://localhost:8080/api/login',{
            email: email,
            password: password
        });
        await this.setToken(response.data['accessToken']);
       return response;
      } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
      }
      }
  
    async setToken(user) {
      localStorage.setItem('token', JSON.stringify(user));
    }

    getToken() {
        const user = JSON.parse(localStorage.getItem('token'));
        return user;
    }

    validateUser() {
        const token = this.getToken();
        if (!token) {
            return null;
        }

        const decodedToken = jwtDecode(token);
        const roles = decodedToken.role;

        return roles ? roles[0] : null;
    }
}
  
  const authService = new AuthService();
  
  export default authService;