package use_case.generate_outfit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static use_case.generate_outfit.OutfitGenerationConstants.*;

class OutfitGenerationInteractorTest {

    private static final InputData emptyInputData = new InputData();
    private static final LocationDataSource locationDataSource = () -> null;
    private static final WeatherDataSource weatherDataSource = location -> basicWeather;
    private static ClothingDataSource clothingDataSource;
    private static OutputBoundary outputBoundary;
    private static OutfitGenerationInteractor interactor;

    @AfterEach
    void cleanup() {
        clothingDataSource = null;
        outputBoundary = null;
        interactor = null;
    }

    @Test
    void givenCorrectData_shouldPrepareSuccessView() {
        clothingDataSource = OutfitGenerationConstants::getBasicWardrobe;
        outputBoundary = new OutputBoundary() {
            @Override
            public void prepareSuccessView(OutfitOutputData outfit) {
                assertTrue(true);
            }

            @Override
            public void prepareFailView(String error) {
                fail();
            }
        };

        interactor = new OutfitGenerationInteractor(
                locationDataSource,
                weatherDataSource,
                clothingDataSource,
                outputBoundary,
                outfitGenerator
        );

        interactor.execute(emptyInputData);
    }

    @Test
    void givenNoClothingItemsExist_shouldPrepareFailureView() {
        clothingDataSource = List::of;
        outputBoundary = new OutputBoundary() {
            @Override
            public void prepareSuccessView(OutfitOutputData outfit) {
                fail();
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue(true);
            }
        };

        interactor = new OutfitGenerationInteractor(
                locationDataSource,
                weatherDataSource,
                clothingDataSource,
                outputBoundary,
                outfitGenerator
        );

        interactor.execute(emptyInputData);
    }
}