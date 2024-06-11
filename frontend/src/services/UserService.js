import httpClient from "../interceptor/interceptor";
import { jwtDecode } from 'jwt-decode';

class UserService {

    async updateStudent(student) {
        try {
            console.log(student);
            const response = await httpClient.put(`http://localhost:8080/api/student/update`, student);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Updating student:', error);
            throw error;
        }
    }

    async updateProfessor(professor) {
        try {
            professor.university = {name: professor.university};
            console.log(professor);
            const response = await httpClient.put(`http://localhost:8080/api/professor/update`, professor);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Updating professor', error);
            throw error;
        }
    }

    async getNotifications() {
        try {
            const response = await httpClient.get(`http://localhost:8080/api/student/notifications`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Getting notifications:', error);
            throw error;
        }
    }

}

const userService = new UserService();
export default  userService;