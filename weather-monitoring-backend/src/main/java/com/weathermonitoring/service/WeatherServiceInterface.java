package com.weathermonitoring.service;

import com.weathermonitoring.model.WeatherData;
import com.weathermonitoring.model.DailySummary;
import com.weathermonitoring.exception.CityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface WeatherServiceInterface {
    WeatherData getCurrentWeather(String city) throws CityNotFoundException;
    List<WeatherData> getWeatherForecast(String city) throws CityNotFoundException;
    DailySummary getDailySummary(String city, LocalDate date) throws CityNotFoundException;
    DailySummary getLatestDailySummary(String city) throws CityNotFoundException;
    List<String> getWeatherAlerts();
    void updateWeatherData();
    List<String> searchCities(String query);
    List<WeatherData> getWeatherHistory(String city, long startTime, long endTime);
}