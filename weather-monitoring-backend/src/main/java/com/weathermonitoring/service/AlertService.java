package com.weathermonitoring.service;

import com.weathermonitoring.model.Alert;
import com.weathermonitoring.model.WeatherData;
import com.weathermonitoring.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    public List<Alert> getAlerts() {
        return alertRepository.findAll();
    }

    public void checkAndCreateAlert(WeatherData weatherData) {
        
    }
}