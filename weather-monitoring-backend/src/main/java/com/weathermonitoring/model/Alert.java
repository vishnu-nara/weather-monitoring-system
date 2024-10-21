package com.weathermonitoring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "alerts")
public class Alert {
    @Id
    private String id;
    private String city;
    private String condition;
    private double threshold;
    private double actualValue;
    private Instant timestamp;


    public Alert(String id, String city, String condition, double threshold, double actualValue, Instant timestamp) {
        this.id = id;
        this.city = city;
        this.condition = condition;
        this.threshold = threshold;
        this.actualValue = actualValue;
        this.timestamp = timestamp;
    }

    public Alert() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getThreshold() {
        return this.threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getActualValue() {
        return this.actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

}