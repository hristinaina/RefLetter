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

    async getAll() {

        try {
            const response = await httpClient.get('http://localhost:8080/api/program/all');
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

    async postProgramFilter(rank, location, researchScore, citationScore) {
        const data = {
            rank: rank,
            location: location,
            researchScore: researchScore,
            citationScore: citationScore
        };

        try {
            const response = await httpClient.post('http://localhost:8080/api/program/filter', data);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error posting data:', error);
            throw error;
        }
    }
    async getAllProfs() {

        try {
            const response = await httpClient.get('http://localhost:8080/api/professor/all');
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    async getMentorshipPrograms(id) {

        try {
            const response = await httpClient.get(`http://localhost:8080/api/mentorship/mentored/${id}`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    async getProfMentorship(id) {

        try {
            const response = await httpClient.get(`http://localhost:8080/api/mentorship`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    }

    async deleteMentorship(id) {
        try {
            const response = await httpClient.delete(`http://localhost:8080/api/mentorship/${id}`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error deleting mentorship:', error);
            throw error;
        }
    }

    async addMentorship(email) {
        try {
            const response = await httpClient.post(`http://localhost:8080/api/mentorship`,{
                email
            });
            console.log(response);
            return response;
        } catch (error) {
            console.error('Creating mentorship:', error);
            throw error;
        }
    }

}

const programService = new ProgramService();
export default  programService;