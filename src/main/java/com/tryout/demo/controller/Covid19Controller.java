package com.tryout.demo.controller;

import com.tryout.demo.Data.History;
import com.tryout.demo.Data.StatisticsResponse;
import com.tryout.demo.service.Covid19Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Covid19Controller {
    @Autowired
    private Covid19Service covid19Service;

    /**
     * Retrieves an array of countries from the Covid19 service.
     *
     * @return         	an array of countries
     */
    @GetMapping("/countries")
    public String[] getCountries() {
        return covid19Service.getCountries();
    }

    /**
     * Retrieves statistics based on the specified country or overall if no country is provided.
     *
     * @param  country	Optional parameter for filtering statistics by country
     * @return         	StatisticsResponse containing the requested statistics
     */
    @GetMapping("/statistics")
    public StatisticsResponse getStatistics(@RequestParam(required = false) String country) {
        if (country != null) {
            return covid19Service.getStatisticsByCountry(country);
        } else {
            return covid19Service.getStatistics();
        }
    }

    /**
     * Retrieves the history for a specific country on a given day.
     *
     * @param  countryName   the name of the country
     * @param  day           the specific day
     * @return               the history data for the specified country on the given day
     */
    @GetMapping("/history")
    public History getHistory(@RequestParam String countryName, @RequestParam String day) {
        return covid19Service.getHistory(countryName, day);
    }
}
