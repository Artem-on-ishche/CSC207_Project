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
        clothingMap.put("Activewear T Shirt", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Backpack", ClothingType.LOWER_BODY);
        clothingMap.put("Belt", ClothingType.HEAD);
        clothingMap.put("Blazer", ClothingType.HEAD);
        clothingMap.put("Blouse", ClothingType.HEAD);
        clothingMap.put("Boat Shoes", ClothingType.HEAD);
        clothingMap.put("Bodysuit", ClothingType.HEAD);
        clothingMap.put("Bomber Jacket", ClothingType.HEAD);
        clothingMap.put("Boot Cut Pants", ClothingType.HEAD);
        clothingMap.put("Boyfriend Pants", ClothingType.HEAD);
        clothingMap.put("Bra", ClothingType.HEAD);
        clothingMap.put("Burmuda Shorts", ClothingType.HEAD);
        clothingMap.put("Bracelet", ClothingType.HEAD);
        clothingMap.put("Button-Down", ClothingType.HEAD);
        clothingMap.put("Capris", ClothingType.HEAD);
        clothingMap.put("Cardigan", ClothingType.HEAD);
        clothingMap.put("Cargo Shorts", ClothingType.HEAD);
        clothingMap.put("Casual Dress", ClothingType.HEAD);
        clothingMap.put("Clogs", ClothingType.HEAD);
        clothingMap.put("Cocktail Dress", ClothingType.HEAD);
        clothingMap.put("Cross Body Bag", ClothingType.HEAD);
        clothingMap.put("Denim Jacket", ClothingType.HEAD);
        clothingMap.put("Dress", ClothingType.HEAD);
        clothingMap.put("Earring", ClothingType.HEAD);
        clothingMap.put("Flats", ClothingType.HEAD);
        clothingMap.put("Fleece Jacket", ClothingType.HEAD);
        clothingMap.put("Formal Dress", ClothingType.HEAD);
        clothingMap.put("Full Bikini", ClothingType.HEAD);
        clothingMap.put("Fur Coat", ClothingType.HEAD);
        clothingMap.put("Gloves", ClothingType.HEAD);
        clothingMap.put("Halter Top", ClothingType.HEAD);
        clothingMap.put("Hoodies", ClothingType.HEAD);
        clothingMap.put("Jean Skirt", ClothingType.HEAD);
        clothingMap.put("Jeans", ClothingType.HEAD);
        clothingMap.put("Jumpsuit", ClothingType.HEAD);
        clothingMap.put("Kimono", ClothingType.HEAD);
        clothingMap.put("Kimonos", ClothingType.HEAD);
        clothingMap.put("Knee Length Skirt", ClothingType.HEAD);
        clothingMap.put("Laptop Bag", ClothingType.HEAD);
        clothingMap.put("Leather Jacket", ClothingType.HEAD);
        clothingMap.put("Leggings", ClothingType.HEAD);
        clothingMap.put("Little Black Dress", ClothingType.HEAD);
        clothingMap.put("Loafers", ClothingType.HEAD);
        clothingMap.put("Maxi Dress", ClothingType.HEAD);
//        clothingMap.put("Maxi Skirt", ClothingType.HEAD);
//        clothingMap.put("Men's Boots", ClothingType.HEAD);
//        clothingMap.put("Men's Dress Shoes", ClothingType.HEAD);
//        clothingMap.put("Men's Hat", ClothingType.HEAD);
//        clothingMap.put("Men's Sandals", ClothingType.HEAD);
//        clothingMap.put("Men's Scarf", ClothingType.HEAD);
//        clothingMap.put("Men's Shorts", ClothingType.HEAD);
//        clothingMap.put("Men's Underwear", ClothingType.HEAD);
//        clothingMap.put("Men's Watch", ClothingType.HEAD);
//        clothingMap.put("Midi Skirt", ClothingType.HEAD);
//        clothingMap.put("Mini Skirt", ClothingType.HEAD);
//        clothingMap.put("Necklace", ClothingType.HEAD);
//        clothingMap.put("One-Piece", ClothingType.HEAD);
//        clothingMap.put("Overalls", ClothingType.HEAD);
//        clothingMap.put("Hat", ClothingType.HEAD);
//        clothingMap.put("Oxfords", ClothingType.);
//        clothingMap.put("Pant Suit", ClothingType.);
//        clothingMap.put("Panties", ClothingType.);
//        clothingMap.put("Peacoat", ClothingType.);
//        clothingMap.put("Platform Shoes", ClothingType.);
//        clothingMap.put("Polos", ClothingType.);
//        clothingMap.put("Prom Dress", ClothingType.);
//        clothingMap.put("Puffy Coat", ClothingType.);
//        clothingMap.put("Pumps", ClothingType.);
//        clothingMap.put("Raincoats", ClothingType.);
//        clothingMap.put("Relaxed Pants", ClothingType.);
//        clothingMap.put("Ring", ClothingType.);
//        clothingMap.put("Robe", ClothingType.);
//        clothingMap.put("Romper", ClothingType.);
//        clothingMap.put("Sarong", ClothingType.);
//        clothingMap.put("Satchel", ClothingType.);
//        clothingMap.put("Skinny Pants", ClothingType.);
//        clothingMap.put("Skirt", ClothingType.);
//        clothingMap.put("Skirt Suit", ClothingType.);
//        clothingMap.put("Sleepwear", ClothingType.);
//        clothingMap.put("Sneakers", ClothingType.);
//        clothingMap.put("Socks", ClothingType.);
//        clothingMap.put("Sports Bra", ClothingType.);
//        clothingMap.put("Spring Jacket", ClothingType.);
//        clothingMap.put("Strapless Dress", ClothingType.);
//        clothingMap.put("Sunglasses", ClothingType.);
//        clothingMap.put("Sweater", ClothingType.);
//        clothingMap.put("Sweatshirt", ClothingType.);
//        clothingMap.put("Swimwear", ClothingType.);
//        clothingMap.put("T-Shirt", ClothingType.);
//        clothingMap.put("Tank Top", ClothingType.);
//        clothingMap.put("", ClothingType.);
//        clothingMap.put("", ClothingType.);
//        clothingMap.put("", ClothingType.);
//        clothingMap.put("", ClothingType.);
//        clothingMap.put("", ClothingType.);
//        clothingMap.put("", ClothingType.);






        // Add more clothing items as needed

        // Get the clothing type based on a specific clothing item
        String inputClothingItem = fashionAPI.identifyClothingItem("https://img.freepik.com/free-photo/black-woman-trendy-grey-leather-jacket-posing-beige-background-studio-winter-autumn-fashion-look_273443-141.jpg");


        ClothingType foundClothingType = clothingMap.get(inputClothingItem);

        System.out.println(foundClothingType);

    }

}
