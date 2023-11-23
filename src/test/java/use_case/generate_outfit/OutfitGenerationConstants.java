package use_case.generate_outfit;

import entity.ClothingItem;
import entity.ClothingType;
import entity.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OutfitGenerationConstants {
    static OutfitGenerator outfitGenerator = new OutfitGenerator(clothingItems -> clothingItems.get(0));

    static Weather basicWeather = new Weather(15, false);

    private static List<ClothingItem> basicWardrobeSingleton;

    static List<ClothingItem> getBasicWardrobe() {
        if (basicWardrobeSingleton == null) {
            generateBasicWardrobe();
        }

        return basicWardrobeSingleton;
    }

    private static void generateBasicWardrobe() {
        basicWardrobeSingleton = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            for (var clothingType : ClothingType.values()) {
                basicWardrobeSingleton.add(new ClothingItem(
                        (long) i,
                        "item",
                        null,
                        clothingType,
                        -i,
                        Optional.empty()
                ));
            }
        }
    }
}
