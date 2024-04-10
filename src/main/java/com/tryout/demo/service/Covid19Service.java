package com.tryout.demo.service;

import com.tryout.demo.Data.CountriesResponse;
import com.tryout.demo.Data.History;
import com.tryout.demo.Data.HistoryApiResponse;
import com.tryout.demo.Data.StatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

@Service
public class Covid19Service {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${covid.api.baseurl}")
    private String baseUrl;

    @Value("${covid.api.endpoints.countries}")
    private String countriesEndpoint;

    @Value("${covid.api.endpoints.statistics}")
    private String statisticsEndpoint;

    /**
     * Retrieves an array of countries by making a GET request to the countries endpoint
     *
     * @return         	the array of countries
     */
    public String[] getCountries() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", rapidApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CountriesResponse> responseEntity = restTemplate.exchange(
                baseUrl + countriesEndpoint, HttpMethod.GET, entity, CountriesResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(responseEntity.getBody()).getResponse();
        } else {
            throw new RuntimeException("Failed to retrieve countries data: " + responseEntity.getStatusCode());
        }
    }

    /**
     * Retrieves statistics using the provided API key and returns the statistics response.
     *
     * @return the statistics response
     */
    public StatisticsResponse getStatistics() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", rapidApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<StatisticsResponse> responseEntity = restTemplate.exchange(
                baseUrl + statisticsEndpoint, HttpMethod.GET, entity, StatisticsResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve statistics data: " + responseEntity.getStatusCode());
        }
    }

    /**
     * Retrieves statistics for a specific country.
     *
     * @param  country	The country for which statistics are requested.
     * @return         	The statistics response for the provided country.
     */
    public StatisticsResponse getStatisticsByCountry(String country) {
        String endpoint = "/statistics?country=" + country;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-key", rapidApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(baseUrl + endpoint));

        return restTemplate.exchange(requestEntity, StatisticsResponse.class).getBody();
    }

    /**
     * Retrieves historical data for a specific country on a given day.
     *
     * @param  countryName    the name of the country to retrieve data for
     * @param  day            the specific day to retrieve data for
     * @return               historical data for the specified country and date
     */
    public History getHistory(String countryName, String day) {
        String historyEndpoint = "/history?country=" + countryName + "&day=" + day;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-key", rapidApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<?> requestEntity = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(baseUrl + historyEndpoint)
        );

        ResponseEntity<HistoryApiResponse> responseEntity = restTemplate.exchange(
                requestEntity, HistoryApiResponse.class
        );

        HistoryApiResponse apiResponse = responseEntity.getBody();

        if (apiResponse != null && apiResponse.getResults() > 0) {
            return apiResponse.getResponse()[0];
        } else {
            throw new RuntimeException("No historical data available for the specified country and date.");
        }
    }
}
