package com.ftn.sbnz.configs;

import com.ftn.sbnz.ModelHelper.CompanyCSV;
import com.ftn.sbnz.ModelHelper.UniversityCSV;
import com.ftn.sbnz.model.models.University;
import com.ftn.sbnz.model.models.Company;
import com.ftn.sbnz.model.repo.CompanyRepo;
import com.ftn.sbnz.model.repo.UniversityRepo;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class LoadDB {
    @Bean
    CommandLineRunner initDatabase(UniversityRepo repository, CompanyRepo companyRepository) {
        return args -> {
            loadUniversityData(repository);

            loadCompanyData(companyRepository);
        };
    }

    private static void loadUniversityData(UniversityRepo repository) throws FileNotFoundException {
        List<UniversityCSV> universitiesCSV = new CsvToBeanBuilder<UniversityCSV>(new FileReader("service/src/main/resources/data/World_University_Rankings_2023.csv"))
                .withType(UniversityCSV.class)
                .build()
                .parse();

        List<University> universities = universitiesCSV.stream().map(universityCSV -> {
            int numberOfStudents = 0;
            try {
                numberOfStudents = Integer.parseInt(universityCSV.getNumberOfStudents().replace(",", ""));
            }
            catch(Exception ignored){
            }
            double internationalStudentPercent = 0;
            try {
                internationalStudentPercent = Double.parseDouble(universityCSV.getInternationalStudentPercent().replace("%", ""));
            }
            catch(Exception ignored){
            }

            return new University(
                    universityCSV.getName(),
                    universityCSV.getLocation(),
                    universityCSV.getRank(),
                    numberOfStudents,
                    universityCSV.getStudentPerStaff(),
                    internationalStudentPercent,
                    universityCSV.getOverallScore(),
                    universityCSV.getResearchScore(),
                    universityCSV.getCitationScore()
            );
        }).collect(Collectors.toList());

        repository.saveAll(universities);
    }

    private static void loadCompanyData(CompanyRepo companyRepository) throws FileNotFoundException {
        List<CompanyCSV> companiesCSV = new CsvToBeanBuilder<CompanyCSV>(new FileReader("service/src/main/resources/data/global-fortune-500-data.csv"))
                .withType(CompanyCSV.class)
                .build()
                .parse();

        List<Company> companies = companiesCSV.stream().map(companyCSV -> {
            int rank = 0;
            try {
                rank = Integer.parseInt(companyCSV.getRank());
            } catch(Exception ignored){
            }

            int employees = 0;
            try {
                employees = Integer.parseInt(companyCSV.getEmployees());
            } catch(Exception ignored){
            }

            double revenue = 0;
            try {
                revenue = Double.parseDouble(companyCSV.getRevenue().replace(",", ".").replace("$", ""));
            } catch(Exception ignored){
            }

            return new Company(
                    rank,
                    companyCSV.getCompany(),
                    companyCSV.getIndustry(),
                    companyCSV.getCity(),
                    companyCSV.getStateCountry(),
                    companyCSV.getCountry(),
                    revenue,
                    employees
            );
        }).collect(Collectors.toList());

        companyRepository.saveAll(companies);
    }


}
