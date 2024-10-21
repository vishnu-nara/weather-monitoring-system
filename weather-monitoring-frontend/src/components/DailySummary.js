import React from 'react';
import '../styles/DailySummary.css';

function DailySummary({ city, data }) {
  if (!data) return null;

  return (
    <div className="daily-summary">
      <h3>{city} - Daily Summary</h3>
      <p>Average: {data.avgTemperature}°C</p>
      <p>Max: {data.maxTemperature}°C</p>
      <p>Min: {data.minTemperature}°C</p>
      <p>Dominant Condition: {data.dominantCondition}</p>
    </div>
  );
}

export default DailySummary;