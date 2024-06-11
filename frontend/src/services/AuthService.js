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
        const token = localStorage.getItem('token');
        let user = null;
        try {
            user = JSON.parse(token);
        } catch (error) {
            console.error('Error parsing token:', error);
        }
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

    async logout() {
      localStorage.removeItem('token');
    }

    async registerStudent(student){
        try {
            console.log(student);
            const response = await httpClient.post('http://localhost:8080/api/register/student',{
                ...student
            });
            await this.setToken(response.data['accessToken']);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    async registerProfessor(professor){
        try {
            console.log(professor);
            const response = await httpClient.post('http://localhost:8080/api/register/professor',{
                ...professor
            });
            await this.setToken(response.data['accessToken']);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }
      
    async getProfileData() {
        try {
          const response = await httpClient.get('http://localhost:8080/api');
          console.log(response);
          return response.data;
        } catch (error) {
          console.error('Error fetching data:', error);
          throw error;
        }
      }
    
}
  
  const authService = new AuthService();
  
  export default authService;