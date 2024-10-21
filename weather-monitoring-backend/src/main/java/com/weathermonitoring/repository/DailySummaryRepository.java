package com.weathermonitoring.repository;

import com.weathermonitoring.model.DailySummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;

public interface DailySummaryRepository extends MongoRepository<DailySummary, String> {
    DailySummary findTopByCityOrderByDateDesc(String city);
    DailySummary findByCityAndDate(String city, LocalDate date);
}