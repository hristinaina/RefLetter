import httpClient from "../interceptor/interceptor";
import { jwtDecode } from 'jwt-decode';

class ProgramService {
    async getRecommendations() {

        try {
            const response = await httpClient.get('http://localhost:8080/api/student/recommendation');
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    async getFinancialAids(programID){
        try {
            const response = await httpClient.get(`http://localhost:8080/api/program/${programID}/details`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }

    }

}

const programService = new ProgramService();
export default  programService;