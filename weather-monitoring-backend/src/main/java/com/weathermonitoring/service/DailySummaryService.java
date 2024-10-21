package com.weathermonitoring.service;

import com.weathermonitoring.model.DailySummary;
import com.weathermonitoring.model.WeatherData;
import com.weathermonitoring.repository.DailySummaryRepository;
import com.weathermonitoring.repository.WeatherDataRepository;
import com.weathermonitoring.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DailySummaryService {

    @Autowired
    private DailySummaryRepository dailySummaryRepository;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    private final List<String> cities = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");

    public DailySummary getDailySummary(String city) {
        DailySummary summary = dailySummaryRepository.findTopByCityOrderByDateDesc(city);
        if (summary == null) {
            throw new CityNotFoundException("No daily summary found for city: " + city);
        }
        return summary;
    }

    public DailySummary getDailySummary(String city, LocalDate date) {
        DailySummary summary = dailySummaryRepository.findByCityAndDate(city, date);
        if (summary == null) {
            throw new CityNotFoundException("No daily summary found for city: " + city + " on date: " + date);
        }
        return summary;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run at midnight every day
    public void generateDailySummaries() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        long startOfDay = yesterday.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        long endOfDay = yesterday.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

        for (String city : cities) {
            List<WeatherData> dailyData = weatherDataRepository.findByNameAndDtBetween(city, startOfDay, endOfDay);

            if (!dailyData.isEmpty()) {
                DailySummary summary = calculateDailySummary(city, yesterday, dailyData);
                dailySummaryRepository.save(summary);
            } else {
                System.out.println("No weather data found for " + city + " on " + yesterday);
            }
        }
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
}