# Weather Monitoring System - Frontend

This is the frontend application for the Weather Monitoring System. It's built with React and provides a user interface for viewing weather data, forecasts, and alerts.

## Features

- Display current weather for multiple cities
- Show weather forecasts
- Present daily weather summaries
- Display weather alerts
- Search functionality for cities

## Prerequisites

Before you begin, ensure you have met the following requirements:
* You have installed Node.js (version 14.0.0 or later)
* You have installed npm (usually comes with Node.js)

## Installing Weather Monitoring System Frontend

To install the Weather Monitoring System Frontend, follow these steps:

1. Clone the repository
   ```
   git clone https://github.com/vishnu-nara/weather-monitoring-system.git 
   ```
2. Navigate to the project directory
   ```
   cd weather-monitoring-frontend
   ```
3. Install the dependencies
   ```
   npm install
   ```

## Configuring the Application

1. Create a `.env` file in the root directory of the project
2. Add the following line to the `.env` file:
   ```
   REACT_APP_API_BASE_URL=http://localhost:8080/api
   ```
   Replace the URL with your backend API URL if it's different.

## Running the Application

To run the Weather Monitoring System Frontend, use the following command:

```
npm start
```

The application will start and can be accessed at `http://localhost:3000`.

## Building for Production

To create a production build, use:

```
npm run build
```

This will create a `build` directory with production-ready files.

## Contact

If you want to contact me, you can reach me at `<your_email@example.com>`.

## License

This project uses the following license: [MIT License](<link_to_license>).
