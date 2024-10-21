package com.weathermonitoring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "weatherData")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    @Id
    private String id;
    private String name;
    private Weather[] weather;
    private Main main;
    private long dt;

    public static class Weather {
        private String main;

        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }
    }

    public static class Main {
        private double temp;
        private double feels_like;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }

        public double getFeels_like() { return feels_like; }
        public void setFeels_like(double feels_like) { this.feels_like = feels_like; }
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Weather[] getWeather() { return weather; }
    public void setWeather(Weather[] weather) { this.weather = weather; }

    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public long getDt() { return dt; }
    public void setDt(long dt) { this.dt = dt; }

    // Convenience method to get the main weather condition
    public String getMainWeatherCondition() {
        return weather != null && weather.length > 0 ? weather[0].main : null;
    }
}