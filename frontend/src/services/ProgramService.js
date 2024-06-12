import { type } from "@testing-library/user-event/dist/type";
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
        }
    }

    async getAll() {

        try {
            const response = await httpClient.get('http://localhost:8080/api/program/all');
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    async getByProfessor() {

        try {
            const response = await httpClient.get('http://localhost:8080/api/program/prof');
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
        }
    }
    async getAllProfs() {

        try {
            const response = await httpClient.get('http://localhost:8080/api/professor/all');
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    async getMentorshipPrograms(id) {

        try {
            const response = await httpClient.get(`http://localhost:8080/api/mentorship/mentored/${id}`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    async getProfMentorship(id) {

        try {
            const response = await httpClient.get(`http://localhost:8080/api/mentorship`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    async deleteMentorship(id) {
        try {
            const response = await httpClient.delete(`http://localhost:8080/api/mentorship/${id}`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error deleting mentorship:', error);
        }
    }

    async deleteProgram(id) {
        try {
            const response = await httpClient.delete(`http://localhost:8080/api/program/${id}`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error deleting mentorship:', error);
            throw error;
        }
    }

    async deleteAid(id, selectedItem) {
        try {
            const response = await httpClient.delete(`http://localhost:8080/api/aid/${id}`, {
                data: { programId: selectedItem } 
            });;
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
        }
    }

    
    async addProgram(student) {
        try {
            const program = {
                name: student.name,
                price: student.price,
                universityName: student.universityName,
                requirement: {
                    gpa: student.gpa,
                    researchExperience: student.researchExperience,
                    researchInterest: student.researchInterest,
                    testScores: student.testScores,
                }
            };
            console.log(program);
            const response = await httpClient.post(`http://localhost:8080/api/program`, program);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Creating mentorship:', error);
            throw error;
        }
    }

    async addAid(student, programId) {
        try {
            console.log(student);
            const aid = {
                financialAid: {
                    type: student.type,
                    deadline: student.deadline,
                    amount: student.price,
                    requirement: {
                        gpa: student.gpa,
                        researchExperience: student.researchExperience,
                        researchInterest: student.researchInterest,
                        testScores: student.testScores,
                    },
                },
                programId: programId,
            };
            const response = await httpClient.post(`http://localhost:8080/api/aid`, aid);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Creating mentorship:', error);
            throw error;
        }
    }

    async checkCriteria(id) {

        try {
            const response = await httpClient.get(`http://localhost:8080/api/student/criteria/${id}`);
            console.log(response);
            return response;
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

}

const programService = new ProgramService();
export default  programService;