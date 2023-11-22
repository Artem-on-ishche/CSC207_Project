package use_case.generate_outfit;

import entity.ClothingItem;
import entity.ClothingType;
import entity.Outfit;
import entity.Weather;

import java.util.*;
import java.util.stream.Collectors;

public class OutfitGenerator {
    private final ClothingItemSelectionStrategy clothingItemSelectionStrategy;

    public OutfitGenerator(ClothingItemSelectionStrategy clothingItemSelectionStrategy) {
        this.clothingItemSelectionStrategy = clothingItemSelectionStrategy;
    }

    public Outfit generateOutfit(
            Weather weather,
            Collection<ClothingItem> allClothingItems
    ) throws OutfitGenerationException {
        var temperatureAppropriateClothingItems = filterClothingItemsByTemperature(allClothingItems, weather.temperature());
        var clothingItemsByCategory = groupClothingItemsByCategory(temperatureAppropriateClothingItems);
        var clothingTypeRequirements = getClothingTypeRequirementBasedOnWeather(weather);
        var isUmbrellaRequired = weather.isRaining();

        return randomlySelectAppropriateOutfit(clothingItemsByCategory, clothingTypeRequirements, isUmbrellaRequired);
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

    private Outfit randomlySelectAppropriateOutfit(
            Map<ClothingType, List<ClothingItem>> clothingItemsByCategory,
            Map<ClothingType, Boolean> clothingTypeRequirements,
            boolean isUmbrellaRequired
    ) throws OutfitGenerationException {
        var outfit = new Outfit(new HashMap<>(), isUmbrellaRequired);
        for (var clothingType : ClothingType.values()) {
            var isNeeded = clothingTypeRequirements.getOrDefault(clothingType, false);

            if (!isNeeded) continue;

            var clothingItemsOfThisCategory = clothingItemsByCategory.get(clothingType);
            if (clothingItemsOfThisCategory.isEmpty()) {
                throw new OutfitGenerationException("No items found in " + clothingType + " category");
            }

            var clothingItem = clothingItemSelectionStrategy.selectClothingItem(clothingItemsOfThisCategory);
            outfit.addClothingItem(clothingItem);
        }

        return outfit;
    }
}
