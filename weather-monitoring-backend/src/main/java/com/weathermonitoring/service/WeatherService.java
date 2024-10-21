package com.weathermonitoring.service;

import com.weathermonitoring.model.WeatherData;
import com.weathermonitoring.model.DailySummary;
import com.weathermonitoring.repository.WeatherDataRepository;
import com.weathermonitoring.repository.DailySummaryRepository;
import com.weathermonitoring.exception.CityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService implements WeatherServiceInterface {

    private final WeatherDataRepository weatherDataRepository;
    private final DailySummaryRepository dailySummaryRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String apiUrl;

    private final List<String> cities = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");

    public WeatherService(WeatherDataRepository weatherDataRepository,
                          DailySummaryRepository dailySummaryRepository,
                          RestTemplate restTemplate,
                          ObjectMapper objectMapper,
                          @Value("${openweathermap.api.key}") String apiKey,
                          @Value("${openweathermap.api.url}") String apiUrl) {
        this.weatherDataRepository = weatherDataRepository;
        this.dailySummaryRepository = dailySummaryRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    @Override
    @Scheduled(fixedRateString = "${weather.update.interval}")
    public void updateWeatherData() {
        for (String city : cities) {
            try {
                String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);
                String jsonResponse = restTemplate.getForObject(url, String.class);
                WeatherData weatherData = objectMapper.readValue(jsonResponse, WeatherData.class);
                weatherDataRepository.save(weatherData);
                checkAndTriggerAlert(weatherData);
            } catch (Exception e) {
                System.err.println("Error updating weather data for " + city + ": " + e.getMessage());
            }
        }
    }

    @Override
    public WeatherData getCurrentWeather(String city) {
        WeatherData weatherData = weatherDataRepository.findTopByNameOrderByDtDesc(city);
        if (weatherData == null) {
            throw new CityNotFoundException("Weather data not found for city: " + city);
        }
        return weatherData;
    }

    @Override
    public List<WeatherData> getWeatherForecast(String city) throws CityNotFoundException {
        throw new UnsupportedOperationException("Weather forecast functionality is not implemented.");
    }

    @Override
    public List<WeatherData> getWeatherHistory(String city, long startTime, long endTime) {
        return weatherDataRepository.findByNameAndDtBetween(city, startTime, endTime);
    }

    @Scheduled(cron = "0 0 0 * * ?") 
    public void generateDailySummaries() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        for (String city : cities) {
            try {
                long startOfDay = yesterday.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
                long endOfDay = yesterday.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
                List<WeatherData> dailyData = weatherDataRepository.findByNameAndDtBetween(city, startOfDay, endOfDay);
                
                if (!dailyData.isEmpty()) {
                    DailySummary summary = calculateDailySummary(city, yesterday, dailyData);
                    dailySummaryRepository.save(summary);
                }
            } catch (Exception e) {
                System.err.println("Error generating daily summary for " + city + ": " + e.getMessage());
            }
        }
    }

    @Override
    public DailySummary getDailySummary(String city, LocalDate date) {
        DailySummary summary = dailySummaryRepository.findByCityAndDate(city, date);
        if (summary == null) {
            throw new CityNotFoundException("Daily summary not found for city: " + city + " on date: " + date);
        }
        return summary;
    }

    @Override
    public DailySummary getLatestDailySummary(String city) throws CityNotFoundException {

        LocalDate latestDate = LocalDate.now().minusDays(1); 
        return getDailySummary(city, latestDate);
    }

    @Override
    public List<String> getWeatherAlerts() {
        return Collections.emptyList();
    }

    @Override
    public List<String> searchCities(String query) {
        return cities.stream()
                .filter(city -> city.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    private DailySummary calculateDailySummary(String city, LocalDate date, List<WeatherData> dailyData) {
        DoubleSummaryStatistics tempStats = dailyData.stream()
                .mapToDouble(data -> data.getMain().getTemp())
                .summaryStatistics();

        Map<String, Long> conditionCounts = dailyData.stream()
                .collect(Collectors.groupingBy(WeatherData::getMainWeatherCondition, Collectors.counting()));

        String dominantCondition = conditionCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        return new DailySummary(
                city,
                date,
                tempStats.getAverage(),
                tempStats.getMax(),
                tempStats.getMin(),
                dominantCondition
        );
    }

    private void checkAndTriggerAlert(WeatherData weatherData) {
        if (weatherData.getMain().getTemp() > 35) {
            System.out.println("ALERT: High temperature in " + weatherData.getName() + ": " + weatherData.getMain().getTemp() + "°C");
        }
    }

    public void cleanupOldData(long olderThan) {
        weatherDataRepository.deleteByDtLessThan(olderThan);
    }
}
