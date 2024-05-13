package com.ftn.sbnz.ModelHelper;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CompanyCSV {
    @CsvBindByName(column = "Rank")
    private String rank;

    @CsvBindByName(column = "Company")
    private String company;

    @CsvBindByName(column = "Industry")
    private String industry;

    @CsvBindByName(column = "City")
    private String city;

    @CsvBindByName(column = "State / Country")
    private String stateCountry;

    @CsvBindByName(column = "Country")
    private String country;

    @CsvBindByName(column = "Revenue (in millions, USD)")
    private String revenue;

    @CsvBindByName(column = "Employees")
    private String employees;


    // getters and setters
}
