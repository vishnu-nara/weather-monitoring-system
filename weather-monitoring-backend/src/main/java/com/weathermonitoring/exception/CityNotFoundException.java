package com.weathermonitoring.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String city) {
        super("Weather data for city '" + city + "' not found");
    }
}