package use_case.create_wardrobe;

import model.ClothingItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WardrobeGeneratingTest {
    private static final CreateInputData defaultInputData = new CreateInputData("item", null, null, 20);
    private static CreateDataAccess createDataAccess;
    private static CreateOutputBoundary createPresenter;
    private static ClothingIdentificationService clothingIdentificationService;
    private static CreateInteractor createInteractor;

    @AfterEach
    void cleanup() {
        createDataAccess = null;
        createPresenter = null;
        clothingIdentificationService = null;
        createInteractor = null;
    }

    @Test
    void givenInvalidInput_shouldPrepareFailView() {
        createDataAccess = new CreateDataAccess() {
            @Override
            public void addClothingItem(ClothingItem clothingItem) {
                fail("Should not be called");
            }
        };

        createPresenter = new CreateOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateOutputData outputData) {
                fail("Should not be called");
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue(error.contains("Error adding clothing item"));
            }
        };

        clothingIdentificationService = image -> {
            throw new RuntimeException("Identification error");
        };

        createInteractor = new CreateInteractor(
                createDataAccess,
                createPresenter,
                clothingIdentificationService
        );

        CreateInputData invalidInputData = new CreateInputData(
                null, null, null, 0)
        ;

        createInteractor.execute(invalidInputData);
    }
}