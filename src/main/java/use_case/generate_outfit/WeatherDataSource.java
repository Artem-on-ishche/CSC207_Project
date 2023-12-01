package use_case.generate_outfit;

import entity.Location;
import entity.Weather;

public interface WeatherDataSource {
    Weather getWeatherData(Location location);
}
