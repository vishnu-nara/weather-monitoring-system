const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';

export async function getCurrentWeather(city) {
  const response = await fetch(`${API_BASE_URL}/weather/current/${city}`);
  if (!response.ok) {
    throw new Error('Failed to fetch current weather');
  }
  return response.json();
}

export async function getWeatherByCoordinates(lat, lon) {
  const response = await fetch(`${API_BASE_URL}/weather/coordinates?lat=${lat}&lon=${lon}`);
  if (!response.ok) {
    throw new Error('Failed to fetch weather by coordinates');
  }
  return response.json();
}

export async function getWeatherForecast(city) {
  const response = await fetch(`${API_BASE_URL}/weather/forecast/${city}`);
  if (!response.ok) {
    throw new Error('Failed to fetch weather forecast');
  }
  return response.json();
}

export async function searchCities(query) {
  const response = await fetch(`${API_BASE_URL}/weather/search?q=${query}`);
  if (!response.ok) {
    throw new Error('Failed to search cities');
  }
  return response.json();
}

export async function getDailySummary(city) {
  const response = await fetch(`${API_BASE_URL}/weather/summary/${city}`);
  if (!response.ok) {
    throw new Error('Failed to fetch daily summary');
  }
  return response.json();
}

export async function getAlerts() {
  const response = await fetch(`${API_BASE_URL}/weather/alerts`);
  if (!response.ok) {
    throw new Error('Failed to fetch alerts');
  }
  return response.json();
}