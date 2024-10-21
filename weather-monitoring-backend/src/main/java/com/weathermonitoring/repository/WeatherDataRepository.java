package com.weathermonitoring.repository;

import com.weathermonitoring.model.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface WeatherDataRepository extends MongoRepository<WeatherData, String> {
    WeatherData findTopByNameOrderByDtDesc(String name);
    List<WeatherData> findByNameAndDtBetween(String name, long startTime, long endTime);
    void deleteByDtLessThan(long timestamp);
}