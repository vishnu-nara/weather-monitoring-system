# Weather Monitoring System - Backend

This is the backend application for the Weather Monitoring System. It's built with Spring Boot and provides RESTful APIs for retrieving and managing weather data.

## Features

- Fetch current weather data for multiple cities
- Retrieve weather forecasts
- Generate and store daily weather summaries
- Manage weather alerts
- City search functionality
- Integration with OpenWeatherMap API

## Prerequisites

Before you begin, ensure you have met the following requirements:
* You have installed Java JDK 21
* You have installed Maven
* You have an OpenWeatherMap API key

## Installing Weather Monitoring System Backend

To install the Weather Monitoring System Backend, follow these steps:

1. Clone the repository
   ```
   git clone https://github.com/vishnu-nara/weather-monitoring-system.git
   ```
2. Navigate to the project directory
   ```
   cd weather-monitoring-backend
   ```

## Configuring the Application

1. Open `src/main/resources/application.properties`
2. Add the following properties:
   ```
   openweathermap.api.key=YOUR_API_KEY
   openweathermap.api.url=https://api.openweathermap.org/data/2.5
   weather.update.interval=300000
   ```
   Replace `YOUR_API_KEY` with your actual OpenWeatherMap API key.

## Running the Application

To run the Weather Monitoring System Backend, use the following command:

```
mvn spring-boot:run
```

The application will start and can be accessed at `http://localhost:8080`.

## API Endpoints

- GET `/api/weather/current/{city}`: Get current weather for a city
- GET `/api/weather/forecast/{city}`: Get weather forecast for a city
- GET `/api/weather/summary/{city}`: Get daily weather summary for a city
- GET `/api/weather/alerts`: Get weather alerts
- POST `/api/weather/update`: Trigger weather data update
- GET `/api/weather/search`: Search for cities

## Building for Production

To create a production build, use:

```
mvn clean package
```

This will create a JAR file in the `target` directory.


## Contact

If you want to contact me, you can reach me at email.

## License

This project uses the following license: [MIT License](<link_to_license>).
