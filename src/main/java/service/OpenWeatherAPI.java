package service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class OpenWeatherAPI implements WeatherDataSource {
    @Override
    public WeatherData getWeatherData() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.open-meteo.com/v1/forecast?latitude=43.6532&longitude=-79.4163&" +
                        "current=temperature_2m,precipitation&timezone=America%2FNew_York&forecast_days=1")
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
        }
    }

    public static void main(String[] args) {
        WeatherDataSource testObject = new OpenWeatherAPI();
        System.out.println(testObject.getWeatherData());
    }
}
