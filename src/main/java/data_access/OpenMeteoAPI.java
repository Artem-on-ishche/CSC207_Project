package data_access;

import entity.Location;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import use_case.generate_outfit.WeatherData;
import use_case.generate_outfit.WeatherDataSource;

import java.io.IOException;

public class OpenMeteoAPI implements WeatherDataSource {

    @Override
    public WeatherData getWeatherData(Location userLocation) {
        double userLong = userLocation.longitude();
        double userLat = userLocation.latitude();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.open-meteo.com/v1/forecast?latitude=" + userLat + "&longitude=" +
                       userLong + "&current=temperature_2m,precipitation&timezone=" +
                        "America%2FNew_York&forecast_days=1")
                .method("GET", null)
                .build();

        try(Response response = client.newCall(request).execute()){
            JSONObject responseBody = new JSONObject(response.body().string()).getJSONObject("current");

            return new WeatherData(
                    responseBody.getDouble("temperature_2m"),
                    responseBody.getDouble("precipitation") > 0
            );
        } catch (IOException ioException) {
            throw new RuntimeException("The weather API call didn't work.");

        } //catch (NullPointerException nullPointerException) {
            //throw new Error("Oopsie");

        }
    //}

    public static void main(String[] args) {
        Location place = new Location(-79.4163, 43.6532);
        WeatherDataSource testObject = new OpenMeteoAPI();
        System.out.println(testObject.getWeatherData(place));
    }
}
