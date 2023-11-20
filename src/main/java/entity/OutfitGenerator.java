package entity;

import java.util.*;
import java.util.stream.Collectors;

public class OutfitGenerator {

    public Outfit generateOutfit(
            Weather weather,
            Collection<ClothingItem> allClothingItems
    ) throws OutfitGenerationException {
        var temperatureAppropriateClothingItems = filterClothingItemsByTemperature(allClothingItems, weather.temperature());
        var clothingItemsByCategory = groupClothingItemsByCategory(temperatureAppropriateClothingItems);
        var clothingTypeRequirements = getClothingTypeRequirementBasedOnWeather(weather);

        return randomlySelectAppropriateOutfit(clothingItemsByCategory, clothingTypeRequirements);
    }

    private List<ClothingItem> filterClothingItemsByTemperature(Collection<ClothingItem> clothingItems, double temperature) {
        return clothingItems
                .stream()
                .filter(clothingItem -> clothingItem.isAppropriateForTemperature(temperature))
                .toList();
    }

    private Map<ClothingType, List<ClothingItem>> groupClothingItemsByCategory(Collection<ClothingItem> clothingItems) {
        return clothingItems.stream().collect(Collectors.groupingBy(ClothingItem::clothingType));
    }

    private Map<ClothingType, Boolean> getClothingTypeRequirementBasedOnWeather(Weather weather) {
        return Arrays.stream(ClothingType.values()).collect(Collectors.toMap(
                clothingType -> clothingType,
                clothingType -> weather.temperature() <= clothingType.maximumAppropriateTemperature
        ));
    }

    public static <E> E getRandom(List<E> list) {
        var randomIndex = new Random().nextInt(list.size());
        return list.get(randomIndex);
    }

    private Outfit randomlySelectAppropriateOutfit(
            Map<ClothingType, List<ClothingItem>> clothingItemsByCategory,
            Map<ClothingType, Boolean> clothingTypeRequirements
    ) throws OutfitGenerationException {
        var outfit = new Outfit(new HashMap<>());
        for (var clothingType : ClothingType.values()) {
            var isNeeded = clothingTypeRequirements.getOrDefault(clothingType, false);

            if (!isNeeded) continue;

            var clothingItemsOfThisCategory = clothingItemsByCategory.get(clothingType);
            if (clothingItemsOfThisCategory.isEmpty()) {
                throw new OutfitGenerationException("No items found in " + clothingType + " category");
            }

            var clothingItem = getRandom(clothingItemsOfThisCategory);
            outfit.addClothingItem(clothingItem);
        }

        return outfit;
    }
}
