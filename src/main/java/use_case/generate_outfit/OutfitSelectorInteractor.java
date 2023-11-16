package use_case.generate_outfit;

import entity.ClothingType;
import entity.ClothingItem;
import entity.Outfit;

import java.util.*;
import java.util.stream.Collectors;

public class OutfitSelectorInteractor implements InputBoundary {

    LocationDataSource locationDataSource;
    WeatherDataSource weatherDataSource;
    ClothingDataSource clothingDataSource;
    OutputBoundary outputBoundary;

    @Override
    public void execute(InputData inputData) {
        var location = locationDataSource.getLocationData();
        var weather = weatherDataSource.getWeatherData(location);
        var allClothingItems = clothingDataSource.getAllClothingItems();
        var clothingItemsByCategory = groupClothingItemsByCategory(allClothingItems);
        var clothingTypeRequirements = getClothingTypeRequirementBasedOnWeather(weather);

        randomlySelectAppropriateOutfit(clothingItemsByCategory, clothingTypeRequirements);
    }

    private Map<ClothingType, List<ClothingItem>> groupClothingItemsByCategory(Collection<ClothingItem> clothingItems) {
        return clothingItems.stream().collect(Collectors.groupingBy(ClothingItem::clothingType));
    }

    private Map<ClothingType, Boolean> getClothingTypeRequirementBasedOnWeather(WeatherData weather) {
        // TODO implement real mapping logic
        return Map.of(
                ClothingType.INNER_UPPER_BODY, true,
                ClothingType.LOWER_BODY, true,
                ClothingType.FOOTWEAR, true
        );
    }

    public static <E> E getRandom(List<E> list) {
        var randomIndex = new Random().nextInt(list.size());
        return list.get(randomIndex);
    }

    private void randomlySelectAppropriateOutfit(
            Map<ClothingType, List<ClothingItem>> clothingItemsByCategory,
            Map<ClothingType, Boolean> clothingTypeRequirements
    ) {
        var outfit = new Outfit(new HashMap<>());
        for (var clothingType : ClothingType.values()) {
            var isNeeded = clothingTypeRequirements.getOrDefault(clothingType, false);

            if (!isNeeded) continue;

            var clothingItemsOfThisCategory = clothingItemsByCategory.get(clothingType);
            if (clothingItemsOfThisCategory.isEmpty()) {
                outputBoundary.prepareFailView("No items found in " + clothingType + " category");

                return;
            }

            var clothingItem = getRandom(clothingItemsOfThisCategory);
            outfit.addClothingItem(clothingItem);
        }

        outputBoundary.prepareSuccessView(new OutfitOutputData(outfit));
    }
}
