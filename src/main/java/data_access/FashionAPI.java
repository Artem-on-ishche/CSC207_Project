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


    public ClothingType identifyClothingItem(String imageUrl) {
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

        Map<String, ClothingType> clothingMap = new HashMap<>();
        //        clothingMap.put("Belt", ClothingType.MIDDLE_UPPER_BODY);
        //        clothingMap.put("Cross Body Bag", ClothingType.FOOTWEAR);
        //        clothingMap.put("Socks", ClothingType.FOOTWEAR);
        //        clothingMap.put("Men's Underwear", ClothingType.INNER_UPPER_BODY);
        //        clothingMap.put("Panties", ClothingType.INNER_UPPER_BODY);
        //        clothingMap.put("Umbrella", ClothingType.INNER_UPPER_BODY);
        //        clothingMap.put("Bra", ClothingType.INNER_UPPER_BODY);

        clothingMap.put("Activewear T Shirt", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Backpack", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Blazer", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Blouse", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Boat Shoes", ClothingType.FOOTWEAR);
        clothingMap.put("Bodysuit", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Bomber Jacket", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Boot Cut Pants", ClothingType.LOWER_BODY);
        clothingMap.put("Boyfriend Pants", ClothingType.LOWER_BODY);
        clothingMap.put("Burmuda Shorts", ClothingType.LOWER_BODY);
        clothingMap.put("Bracelet", ClothingType.HANDS);
        clothingMap.put("Button-Down", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Capris", ClothingType.LOWER_BODY);
        clothingMap.put("Cardigan", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Cargo Shorts", ClothingType.LOWER_BODY);
        clothingMap.put("Casual Dress", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Clogs", ClothingType.FOOTWEAR);
        clothingMap.put("Cocktail Dress", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Denim Jacket", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Dress", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Earring", ClothingType.HEAD);
        clothingMap.put("Flats", ClothingType.FOOTWEAR);
        clothingMap.put("Fleece Jacket", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Formal Dress", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Full Bikini", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Fur Coat", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Gloves", ClothingType.HANDS);
        clothingMap.put("Halter Top", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Hoodies", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Jean Skirt", ClothingType.LOWER_BODY);
        clothingMap.put("Jeans", ClothingType.LOWER_BODY);
        clothingMap.put("Jumpsuit", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Kimono", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Kimonos", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Knee Length Skirt", ClothingType.LOWER_BODY);
        clothingMap.put("Laptop Bag", ClothingType.HANDS);
        clothingMap.put("Leather Jacket", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Leggings", ClothingType.LOWER_BODY);
        clothingMap.put("Little Black Dress", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Loafers", ClothingType.FOOTWEAR);
        clothingMap.put("Maxi Dress", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Maxi Skirt", ClothingType.LOWER_BODY);
        clothingMap.put("Men's Boots", ClothingType.FOOTWEAR);
        clothingMap.put("Men's Dress Shoes", ClothingType.FOOTWEAR);
        clothingMap.put("Men's Hat", ClothingType.HEAD);
        clothingMap.put("Men's Sandals", ClothingType.FOOTWEAR);
        clothingMap.put("Men's Scarf", ClothingType.NECK);
        clothingMap.put("Men's Shorts", ClothingType.LOWER_BODY);
        clothingMap.put("Men's Watch", ClothingType.HANDS);
        clothingMap.put("Midi Skirt", ClothingType.LOWER_BODY);
        clothingMap.put("Mini Skirt", ClothingType.LOWER_BODY);
        clothingMap.put("Necklace", ClothingType.NECK);
        clothingMap.put("One-Piece", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Overalls", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Hat", ClothingType.HEAD);
        clothingMap.put("Oxfords", ClothingType.FOOTWEAR);
        clothingMap.put("Pant Suit", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Peacoat", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Platform Shoes", ClothingType.FOOTWEAR);
        clothingMap.put("Polos", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Prom Dress", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Puffy Coat", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Pumps", ClothingType.FOOTWEAR);
        clothingMap.put("Raincoats", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Relaxed Pants", ClothingType.LOWER_BODY);
        clothingMap.put("Ring", ClothingType.HANDS);
        clothingMap.put("Robe", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Romper", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Sarong", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Satchel", ClothingType.HANDS);
        clothingMap.put("Skinny Pants", ClothingType.LOWER_BODY);
        clothingMap.put("Skirt", ClothingType.LOWER_BODY);
        clothingMap.put("Skirt Suit", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Sleepwear", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Sneakers", ClothingType.FOOTWEAR);
        clothingMap.put("Sports Bra", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Spring Jacket", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Strapless Dress", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Sunglasses", ClothingType.HEAD);
        clothingMap.put("Sweater", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Sweatshirt", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Swimwear", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("T-Shirt", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Tank Top", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Tankini", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Tie", ClothingType.NECK);
        clothingMap.put("Tights", ClothingType.LOWER_BODY);
        clothingMap.put("Tote Bag", ClothingType.HANDS);
        clothingMap.put("Tracksuit", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Tube Top", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Tunic", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Turtleneck", ClothingType.INNER_UPPER_BODY);
        clothingMap.put("Vest", ClothingType.OUTER_UPPER_BODY);
        clothingMap.put("Wallet", ClothingType.HANDS);
        clothingMap.put("Wedding Dress", ClothingType.MIDDLE_UPPER_BODY);
        clothingMap.put("Wide Leg Pants", ClothingType.LOWER_BODY);
        clothingMap.put("Women's Board Shorts", ClothingType.LOWER_BODY);
        clothingMap.put("Women's Boots", ClothingType.FOOTWEAR);
        clothingMap.put("Women's Hat", ClothingType.HEAD);
        clothingMap.put("Women's Jean Shorts", ClothingType.LOWER_BODY);
        clothingMap.put("Women's Sandals", ClothingType.FOOTWEAR);
        clothingMap.put("Women's Scarf", ClothingType.NECK);
        clothingMap.put("Women's Short Shorts", ClothingType.LOWER_BODY);
        clothingMap.put("Women's Shorts", ClothingType.LOWER_BODY);
        clothingMap.put("Women's Watch", ClothingType.HANDS);
        clothingMap.put("Wristlet & Clutch", ClothingType.HANDS);

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


            String get_ClothingType = responseJSONObject.getJSONArray("outputs").getJSONObject(0).getJSONObject("data").getJSONArray("concepts").getJSONObject(0).getString("name");

            if (!clothingMap.containsKey(get_ClothingType)) {
                throw new IllegalArgumentException("Object is invalid.");

            } else {
                return clothingMap.get(get_ClothingType);
            }

        }

        catch (IllegalArgumentException e){
            throw new RuntimeException("Object is invalid.", e);
        }

        catch (Exception e) {
            throw new RuntimeException("Fashion API failed call.");
        }
    }


    public static void main(String[] args) {
        FashionAPI fashionAPI = new FashionAPI();

        ClothingType Clothingtype = fashionAPI.identifyClothingItem("https://img.freepik.com/free-photo/black-woman-trendy-grey-leather-jacket-posing-beige-background-studio-winter-autumn-fashion-look_273443-141.jpg");

        System.out.println(Clothingtype);

    }

}
