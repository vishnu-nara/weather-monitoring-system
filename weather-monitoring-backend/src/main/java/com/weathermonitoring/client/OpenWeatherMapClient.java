package com.weathermonitoring.client;

import com.weathermonitoring.model.WeatherData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openWeatherMap", url = "${openweathermap.api.url}")
public interface OpenWeatherMapClient {
    @GetMapping
    WeatherData getWeatherData(@RequestParam("q") String city, 
                               @RequestParam("appid") String apiKey);
}