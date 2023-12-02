package data_access;

import model.Location;
import org.json.JSONObject;
import use_case.generate_outfit.LocationDataSource;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IpAddressLocationDataSource implements LocationDataSource {

    private static final String PUBLIC_IP_ADDRESS_API_URI = "https://api.ipify.org";
    private static final String LATITUDE_KEY = "lat";
    private static final String LONGITUDE_KEY = "lon";
    private static final String LOCATION_BY_IP_ADDRESS_API_URI = "http://ip-api.com/json/%s?fields=" + LATITUDE_KEY + "," + LONGITUDE_KEY;

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public Location getLocationData() {
        var ipAddress = getIpAddress();
        return getLocationOfIpAddress(ipAddress);
    }

    private String getIpAddress() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(PUBLIC_IP_ADDRESS_API_URI))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Location getLocationOfIpAddress(String ipAddress) {
        var requestUri = LOCATION_BY_IP_ADDRESS_API_URI.formatted(ipAddress);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var responseJson = new JSONObject(response.body());

            return new Location(responseJson.getDouble(LONGITUDE_KEY), responseJson.getDouble(LATITUDE_KEY));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


