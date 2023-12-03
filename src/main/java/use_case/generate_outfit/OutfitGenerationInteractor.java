package use_case.generate_outfit;

import business_rules.OutfitGenerationException;
import business_rules.OutfitGenerator;

public class OutfitGenerationInteractor implements InputBoundary {

    private final LocationDataSource locationDataSource;
    private final WeatherDataSource weatherDataSource;
    private final ClothingDataSource clothingDataSource;
    private final OutputBoundary outputBoundary;
    private final OutfitGenerator outfitGenerator;

    public OutfitGenerationInteractor(
            LocationDataSource locationDataSource,
            WeatherDataSource weatherDataSource,
            ClothingDataSource clothingDataSource,
            OutputBoundary outputBoundary,
            OutfitGenerator outfitGenerator
    ) {
        this.locationDataSource = locationDataSource;
        this.weatherDataSource = weatherDataSource;
        this.clothingDataSource = clothingDataSource;
        this.outputBoundary = outputBoundary;
        this.outfitGenerator = outfitGenerator;
    }

    @Override
    public void execute(InputData inputData) {
        var location = locationDataSource.getLocationData();
        var weather = weatherDataSource.getWeatherData(location);
        var allClothingItems = clothingDataSource.getAllClothingItemsForUser(inputData.username());

        try {
            var outfit = outfitGenerator.generateOutfit(weather, allClothingItems);
            outputBoundary.prepareSuccessView(new OutfitOutputData(outfit));
        } catch (OutfitGenerationException e) {
            outputBoundary.prepareFailView(e.getMessage());
        }
    }
}
