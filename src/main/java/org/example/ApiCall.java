package org.example;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class ApiCall {
    public static void callApi() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.open-meteo.com/v1/forecast?latitude=43.67&longitude=-79.39&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject responseBody = new JSONObject(response.body().string());
        System.out.println(responseBody);
    }
}
