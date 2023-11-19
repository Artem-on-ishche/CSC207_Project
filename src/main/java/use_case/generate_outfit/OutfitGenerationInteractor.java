package use_case.generate_outfit;

import entity.ClothingType;
import entity.ClothingItem;
import entity.Outfit;

import java.util.*;
import java.util.stream.Collectors;

public class OutfitGenerationInteractor implements InputBoundary {

    LocationDataSource locationDataSource;
    WeatherDataSource weatherDataSource;
    ClothingDataSource clothingDataSource;
    OutputBoundary outputBoundary;

    @Override
    public void execute(InputData inputData) {
        var location = locationDataSource.getLocationData();
        var weather = weatherDataSource.getWeatherData(location);
        var allClothingItems = clothingDataSource.getAllClothingItems();

        var temperatureAppropriateClothingItems = filterClothingItemsByTemperature(allClothingItems, weather.temperature());
        var clothingItemsByCategory = groupClothingItemsByCategory(temperatureAppropriateClothingItems);
        var clothingTypeRequirements = getClothingTypeRequirementBasedOnWeather(weather);

        randomlySelectAppropriateOutfit(clothingItemsByCategory, clothingTypeRequirements);
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

    private Map<ClothingType, Boolean> getClothingTypeRequirementBasedOnWeather(WeatherData weather) {
        return Arrays.stream(ClothingType.values()).collect(Collectors.toMap(
                clothingType -> clothingType,
                clothingType -> weather.temperature() <= clothingType.maximumAppropriateTemperature
        ));
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
