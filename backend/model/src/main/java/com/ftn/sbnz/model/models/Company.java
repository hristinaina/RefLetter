package com.ftn.sbnz.model.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rank;
    private String name;
    private String industry;
    private String city;
    private String stateCountry;
    private String country;
    private double revenue;
    private int employees;


    public Company(int rank, String company, String industry, String city, String stateCountry, String country, double revenue, int employees) {
        this.rank = rank;
        this.name = company;
        this.industry = industry;
        this.city = city;
        this.stateCountry = stateCountry;
        this.country = country;
        this.revenue = revenue;
        this.employees = employees;
    }
}
