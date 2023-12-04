package data_access;

import model.Location;
import org.junit.jupiter.api.Test;
import use_case.generate_outfit.WeatherDataSource;

import static org.junit.jupiter.api.Assertions.*;

class OpenMeteoAPITest {

    @Test
    void getWeatherData() {
        Location place = new Location(-79.4163, 43.6532);
        WeatherDataSource testObject = new OpenMeteoAPI();
        testObject.getWeatherData(place);
    }
}