package use_case.generate_outfit;

import model.Location;
import model.Weather;

public interface WeatherDataSource {
    Weather getWeatherData(Location location);
}
