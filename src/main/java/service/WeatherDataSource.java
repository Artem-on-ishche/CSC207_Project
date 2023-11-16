package service;

import entities.Location;

public interface WeatherDataSource {
    WeatherData getWeatherData(Location location);
}
