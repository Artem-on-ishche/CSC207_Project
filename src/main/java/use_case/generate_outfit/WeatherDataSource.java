package use_case.generate_outfit;

import entity.Location;

public interface WeatherDataSource {
    WeatherData getWeatherData(Location location);
}
