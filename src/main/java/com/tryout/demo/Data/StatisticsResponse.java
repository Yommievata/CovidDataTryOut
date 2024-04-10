package com.tryout.demo.Data;

import lombok.Data;

@Data
public class StatisticsResponse {
    private Statistics[] response;
}

@Data
class Statistics {
    private String continent;
    private String country;
    private long population;
    private Cases cases;
    private Deaths deaths;
    private Tests tests;
    private String day;
    private String time;
}

@Data
class Cases {
    private String newCases;
    private long active;
    private long critical;
    private long recovered;
    private String oneMillionPopulation;
    private long total;
}

@Data
class Deaths {
    private String newDeaths;
    private String oneMillionPopulation;
    private long total;
}

@Data
class Tests {
    private String oneMillionPopulation;
    private long total;
}
