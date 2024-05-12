package com.ftn.sbnz.configs;

import com.ftn.sbnz.ModelHelper.UniversityCSV;
import com.ftn.sbnz.model.models.University;
import com.ftn.sbnz.model.repo.UniversityRepo;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class LoadDB {
    @Bean
    CommandLineRunner initDatabase(UniversityRepo repository) {
        return args -> {
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
        };
    }
}
