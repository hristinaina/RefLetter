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
            console.error('Creating mentorship:', error);
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
            console.error('Creating mentorship:', error);
            throw error;
        }
    }

}

const userService = new UserService();
export default  userService;