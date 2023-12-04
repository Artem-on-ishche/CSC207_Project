package data_access.api;

import data_access.api.OpenMeteoAPI;
import model.Location;
import org.junit.jupiter.api.Test;
import use_case.generate_outfit.WeatherDataSource;

class OpenMeteoAPITest {

    @Test
    void getWeatherData() {
        Location place = new Location(-79.4163, 43.6532);
        WeatherDataSource testObject = new OpenMeteoAPI();
        testObject.getWeatherData(place);
    }
}