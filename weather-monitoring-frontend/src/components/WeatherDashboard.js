import React, { useState } from 'react';
import { getCurrentWeather } from '../services/api';
import './WeatherDashboard.css';

const kelvinToCelsius = (kelvin) => Math.round(kelvin - 273.15);
const kelvinToFahrenheit = (kelvin) => Math.round((kelvin - 273.15) * 9/5 + 32);

function WeatherDashboard() {
  const [city, setCity] = useState('');
  const [weatherData, setWeatherData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [alerts, setAlerts] = useState([]);
  const [unit, setUnit] = useState('Celsius');

  const fetchWeatherData = async (cityName) => {
    setLoading(true);
    setError(null);
    try {
      const result = await getCurrentWeather(cityName);
      const tempCelsius = kelvinToCelsius(result.temperature);
      const tempFahrenheit = kelvinToFahrenheit(result.temperature);
      const feelsLikeCelsius = kelvinToCelsius(result.feelsLike);
      const feelsLikeFahrenheit = kelvinToFahrenheit(result.feelsLike);
      
      const weatherObj = {
        ...result,
        temperatureCelsius: tempCelsius,
        temperatureFahrenheit: tempFahrenheit,
        feelsLikeCelsius: feelsLikeCelsius,
        feelsLikeFahrenheit: feelsLikeFahrenheit,
        temperatureKelvin: result.temperature,
        feelsLikeKelvin: result.feelsLike
      };

      setWeatherData(weatherObj);
      
      // Generate alerts based on temperature
      const newAlerts = [];
      if (tempCelsius > 40) {
        newAlerts.push({ id: `${cityName}-extreme-heat`, city: cityName, condition: 'Extreme Heat', actualValue: tempCelsius });
      } else if (tempCelsius > 25) {
        newAlerts.push({ id: `${cityName}-high-heat`, city: cityName, condition: 'High Heat', actualValue: tempCelsius });
      } else if (tempCelsius < 0) {
        newAlerts.push({ id: `${cityName}-freezing`, city: cityName, condition: 'Freezing Temperature', actualValue: tempCelsius });
      }
      setAlerts(newAlerts);
    } catch (err) {
      console.error('Error fetching weather data:', err);
      setError(`Failed to fetch weather data: ${err.message}`);
      setWeatherData(null);
      setAlerts([]);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (city.trim()) {
      fetchWeatherData(city);
    }
  };

  return (
    <div className="weather-dashboard">
      <h1>Weather Information</h1>
      <form onSubmit={handleSubmit} className="weather-form">
        <input
          type="text"
          value={city}
          onChange={(e) => setCity(e.target.value)}
          placeholder="Enter city name"
          className="city-input"
        />
        <button type="submit" className="submit-button">Get Weather</button>
      </form>
      
      <div className="unit-toggle">
        <label>
          <input
            type="radio"
            value="Celsius"
            checked={unit === 'Celsius'}
            onChange={(e) => setUnit(e.target.value)}
          />
          Celsius
        </label>
        <label>
          <input
            type="radio"
            value="Fahrenheit"
            checked={unit === 'Fahrenheit'}
            onChange={(e) => setUnit(e.target.value)}
          />
          Fahrenheit
        </label>
        <label>
          <input
            type="radio"
            value="Kelvin"
            checked={unit === 'Kelvin'}
            onChange={(e) => setUnit(e.target.value)}
          />
          Kelvin
        </label>
      </div>
      
      {loading && <p className="loading">Loading weather data...</p>}
      
      {error && <p className="error">{error}</p>}
      
      {alerts.length > 0 && (
        <div className="alerts">
          {alerts.map(alert => (
            <div key={alert.id} className={`alert ${alert.actualValue > 35 ? 'alert-hot' : ''}`}>
              <strong>{alert.condition}</strong>: {alert.city} - {alert.actualValue}°C
            </div>
          ))}
        </div>
      )}
      
      {weatherData && (
        <div className="weather-card">
          <h2>{city}</h2>
          <p>
            Temperature: {unit === 'Celsius' ? `${weatherData.temperatureCelsius}°C` : 
                          unit === 'Fahrenheit' ? `${weatherData.temperatureFahrenheit}°F` : 
                          `${weatherData.temperatureKelvin}K`}
          </p>
          <p>
            Feels Like: {unit === 'Celsius' ? `${weatherData.feelsLikeCelsius}°C` : 
                         unit === 'Fahrenheit' ? `${weatherData.feelsLikeFahrenheit}°F` : 
                         `${weatherData.feelsLikeKelvin}K`}
          </p>
          <p>Condition: {weatherData.mainWeatherCondition}</p>
        </div>
      )}
    </div>
  );
}

export default WeatherDashboard;
