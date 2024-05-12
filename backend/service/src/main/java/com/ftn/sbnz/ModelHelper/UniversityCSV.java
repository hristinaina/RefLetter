package com.ftn.sbnz.ModelHelper;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityCSV {
    @CsvBindByName(column = "University Rank")
    private String rank;

    @CsvBindByName(column = "Name of University")
    private String name;

    @CsvBindByName(column = "Location")
    private String location;

    @CsvBindByName(column = "No of student")
    private String numberOfStudents;

    @CsvBindByName(column = "No of student per staff")
    private double studentPerStaff;

    @CsvBindByName(column = "International Student")
    private String internationalStudentPercent;

    @CsvBindByName(column = "OverAll Score")
    private String overallScore;

    @CsvBindByName(column = "Research Score")
    private double researchScore;

    @CsvBindByName(column = "Citations Score")
    private double citationScore;


}