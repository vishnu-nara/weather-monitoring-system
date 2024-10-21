package com.weathermonitoring.exception;

public class InvalidTemperatureThresholdException extends RuntimeException {
    public InvalidTemperatureThresholdException(String message) {
        super(message);
    }
}