package com.weathermonitoring.controller;

import com.weathermonitoring.model.WeatherData;
import com.weathermonitoring.model.DailySummary;
import com.weathermonitoring.service.WeatherService;
import com.weathermonitoring.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current/{city}")
    public ResponseEntity<Map<String, Object>> getCurrentWeather(@PathVariable String city) {
        try {
            WeatherData weatherData = weatherService.getCurrentWeather(city);
            Map<String, Object> response = new HashMap<>();
            response.put("name", weatherData.getName());
            response.put("mainWeatherCondition", weatherData.getMainWeatherCondition());
            response.put("temperature", weatherData.getMain().getTemp());
            response.put("feelsLike", weatherData.getMain().getFeels_like());
            response.put("dt", weatherData.getDt());
            return ResponseEntity.ok(response);
        } catch (CityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/forecast/{city}")
    public ResponseEntity<List<WeatherData>> getWeatherForecast(@PathVariable String city) {
        try {
            List<WeatherData> forecast = weatherService.getWeatherForecast(city);
            return ResponseEntity.ok(forecast);
        } catch (CityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/summary/{city}")
    public ResponseEntity<DailySummary> getDailySummary(@PathVariable String city,
                                                        @RequestParam(required = false) LocalDate date) {
        try {
            DailySummary summary = (date != null) 
                ? weatherService.getDailySummary(city, date)
                : weatherService.getLatestDailySummary(city);
            return ResponseEntity.ok(summary);
        } catch (CityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<String>> getWeatherAlerts() {
        List<String> alerts = weatherService.getWeatherAlerts();
        return ResponseEntity.ok(alerts);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateWeatherData() {
        try {
            weatherService.updateWeatherData();
            return ResponseEntity.ok("Weather data update triggered successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to update weather data: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchCities(@RequestParam String query) {
        List<String> cities = weatherService.searchCities(query);
        return ResponseEntity.ok(cities);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
    }
}