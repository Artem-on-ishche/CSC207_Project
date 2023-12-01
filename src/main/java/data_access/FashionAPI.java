package data_access;



import model.ClothingType;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class FashionAPI {
    private static final String API_KEY = "9a77e212eaab431faddb9de943af7bf6";
    private static final String API_URL = "https://api.clarifai.com/v2/users/clarifai/apps/main/models/apparel-recognition/versions/dc2cd6d9bff5425a80bfe0c4105583c1/outputs";


    public String identifyClothingItem(String imageUrl) {
        String requestBody = "{\n" +
                "  \"inputs\": [\n" +
                "    {\n" +
                "      \"data\": {\n" +
                "        \"image\": {\n" +
                "          \"url\": \"" + imageUrl + "\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Key " + API_KEY);
        headers.put("Content-Type", "application/json");


        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .headers(headers.entrySet().stream()
                            .map(e -> new String[]{e.getKey(), e.getValue()})
                            .flatMap(Arrays::stream)
                            .toArray(String[]::new))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseJSONObject = new JSONObject(response.body());

            return responseJSONObject.getJSONArray("outputs").getJSONObject(0).getJSONObject("data").getJSONArray("concepts").getJSONObject(0).getString("name");

        } catch (Exception e) {
            throw new RuntimeException("Fashion API failed call.");
        }
    }



    public static void main(String[] args) {
        FashionAPI fashionAPI = new FashionAPI();
        Map<String, ClothingType> clothingMap = new HashMap<>();

        // Add clothing items to the map
        clothingMap.put("T-shirt", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Jeans", ClothingType.LOWER_BODY);
        clothingMap.put("Hat", ClothingType.HEAD);
        // Add more clothing items as needed

        // Get the clothing type based on a specific clothing item
        String inputClothingItem = fashionAPI.identifyClothingItem("https://img.freepik.com/free-photo/black-woman-trendy-grey-leather-jacket-posing-beige-background-studio-winter-autumn-fashion-look_273443-141.jpg");

        ClothingType foundClothingType = clothingMap.get(inputClothingItem);

        System.out.println(foundClothingType);

    }

}
