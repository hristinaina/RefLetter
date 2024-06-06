import httpClient from "../interceptor/interceptor";

class AuthService {
  
    async loginUser(email, password) {
      try {
        const response = await httpClient.get('http://localhost:8081/api/users/login');
        
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
      if (!user) {
        window.location.href = '/';
      }
      return user;
    }
  }
  
  const authService = new AuthService();
  
  export default authService;